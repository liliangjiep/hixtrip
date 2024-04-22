package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Inventory getInventory(String skuId) {
        try {
            // 获取指定 skuId 的商品库存信息的哈希结构映射
            Map<Object, Object> inventoryMap = redisTemplate.opsForHash().entries("inventory:" + skuId);
            if (inventoryMap == null || inventoryMap.isEmpty()) {
                return new Inventory(); // 或者返回一个新的Inventory对象，其字段为默认值
            }
            Inventory inventory = new Inventory();
            inventory.setSkuId(skuId);
            inventory.setSellableQuantity((Long) inventoryMap.getOrDefault("sellableQuantity", 0L));
            inventory.setWithholdingQuantity((Long) inventoryMap.getOrDefault("withholdingQuantity", 0L));
            inventory.setOccupiedQuantity((Long) inventoryMap.getOrDefault("occupiedQuantity", 0L));
            return inventory;
        } catch (Exception e) {
            e.printStackTrace(); // 可以记录日志或者其他处理
            return new Inventory(); // 或者抛出自定义异常，或者返回一个新的Inventory对象，其字段为默认值
        }
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        try {
            /**
             *这段代码是一个 Lua 脚本，用于在 Redis 中执行库存扣减操作。它首先从 Redis 中获取当前的可售库存、预占库存和占用库存数量，
             * 然后根据传入的参数进行比较操作。如果可售库存和预占库存都大于等于要扣减的数量，则进行扣减操作，
             * 并增加占用库存，最后返回1表示扣减成功；否则返回0表示扣减失败。这个脚本保证了在高并发情况下的原子性操作，避免了超卖问题。
             */
            // 使用 Lua 脚本扣减库存，保证原子性
            String script = "local skuId = KEYS[1]\n" +
                    "local sellableQuantity = tonumber(ARGV[1])\n" +
                    "local withholdingQuantity = tonumber(ARGV[2])\n" +
                    "local occupiedQuantity = tonumber(ARGV[3])\n" +
                    "local currentSellable = tonumber(redis.call('GET', 'sellableQuantity:' .. skuId) or 0)\n" +
                    "local currentWithholding = tonumber(redis.call('GET', 'withholdingQuantity:' .. skuId) or 0)\n" +
                    "local currentOccupied = tonumber(redis.call('GET', 'occupiedQuantity:' .. skuId) or 0)\n" +
                    "if (currentSellable >= sellableQuantity) and (currentWithholding >= withholdingQuantity) then\n" +
                    "    redis.call('INCRBY', 'sellableQuantity:' .. skuId, -sellableQuantity)\n" +
                    "    redis.call('INCRBY', 'withholdingQuantity:' .. skuId, -withholdingQuantity)\n" +
                    "    redis.call('INCRBY', 'occupiedQuantity:' .. skuId, occupiedQuantity)\n" +
                    "    return 1\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            redisScript.setResultType(Long.class);
            // 执行 Lua 脚本
            Long result = (Long) redisTemplate.execute(redisScript, Collections.singletonList(skuId),
                    sellableQuantity.toString(), withholdingQuantity.toString(), occupiedQuantity.toString());
            // 返回扣减结果
            return result != null && result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
