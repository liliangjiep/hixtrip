package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取sku当前库存
     * @param skuId
     * @return {@link Inventory}
     * @Author: 李良杰
     * @Date：2024/4/22 11:30
     */
    Inventory getInventory(String skuId);
    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return {@link Boolean}
     * @Author: 李良杰
     * @Date：2024/4/22 11:30
     */
    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
