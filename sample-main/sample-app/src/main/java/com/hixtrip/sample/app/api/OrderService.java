package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;

/**
 * 订单的service层
 */
public interface OrderService {
    /**
     * 创建订单并进行库存扣减。
     *
     * @param commandOderCreateDTO
     * @return
     * @Author: 李良杰
     * @Date：2024/4/18 22:30
     */
    void createOrder(CommandOderCreateDTO commandOderCreateDTO);

}
