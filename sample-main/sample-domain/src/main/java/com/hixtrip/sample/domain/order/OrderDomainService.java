package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        orderRepository.createOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public Boolean orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参 这里为了简单实现用了map处理，实际上要定义修改对象
        LocalDateTime updateTime = LocalDateTime.now();
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", commandPay.getOrderId());
        params.put("payStatus", commandPay.getPayStatus());
        params.put("updateTime", updateTime);
        params.put("updateBy", "test");
        int affectedRows = orderRepository.updatePayStatus(params);
        return affectedRows > 0;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public Boolean orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参 这这里为了简单实现用了map处理，实际上要定义修改对象
        LocalDateTime updateTime = LocalDateTime.now();
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", commandPay.getOrderId());
        params.put("payStatus", commandPay.getPayStatus());
        params.put("updateTime", updateTime);
        params.put("updateBy", "test");
        int affectedRows = orderRepository.updatePayStatus(params);
        return affectedRows > 0;
    }

}
