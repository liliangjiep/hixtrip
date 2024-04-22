package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 获取库存信息
        Inventory inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        // 检查库存是否充足
        if (inventory.getSellableQuantity() < commandOderCreateDTO.getAmount()) {
            throw new RuntimeException("库存不足");
        }
        //调用扣减库存方法 减少可售库存，增加预占库存,占用库存不变
        Long amout = commandOderCreateDTO.getAmount().longValue();
        //使用 Lua 脚本扣减库存，保证原子性
        inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), -amout, amout, 0L);
        // 查询SKU价格（模拟）
        BigDecimal price = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        BigDecimal money = BigDecimal.ZERO;
        if (price != null && !price.equals(BigDecimal.ZERO)) {
            //计算购买金额
            money = price.multiply(BigDecimal.valueOf(commandOderCreateDTO.getAmount()));
            //价格为空省略代码
        }
        // 调用 createOrder方法生成订单 省略创建人赋值问题
        Order order = Order.createOrder(commandOderCreateDTO.getUserId(), commandOderCreateDTO.getSkuId(), commandOderCreateDTO.getAmount(), money);
        this.orderDomainService.createOrder(order);
    }
}
