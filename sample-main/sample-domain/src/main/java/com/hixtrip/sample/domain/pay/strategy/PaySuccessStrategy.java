package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author : 李良杰
 * @Description :支付成功策略
 * @Date : 2024/4/18 18:50
 * version :1.0
 **/
@Component
public class PaySuccessStrategy implements PayCallbackStrategy {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void handlePayCallback(CommandPay commandPay) {
        // 获取回调请求中的订单号
        String orderId = commandPay.getOrderId();
        // 判断订单号是否已经处理过，如果已处理则视为重复支付
        if (isOrderProcessed(orderId)) {
            // 订单已处理，执行重复支付逻辑 已处理代表已经处理过了库存就不进行扣减
            //记录支付回调结果
            payDomainService.payRecord(commandPay);
            System.out.println("订单已处理，重复支付逻辑...");
        } else {
            System.out.println("订单未处理，进行正常处理流程...");
            // 订单未处理，进行正常处理流程
            // 执行支付成功逻辑
            orderDomainService.orderPaySuccess(commandPay);
            //记录支付回调结果
            payDomainService.payRecord(commandPay);
            //查询订单，获取订单数量和skuId 代码省略
            //调用扣减库存方法 支付成功：可售库存不变，减少预占库存，增加占用库存
            Long amout = 200L;
            String skuId = "123";
            inventoryDomainService.changeInventory(skuId, 0L, -amout, amout);
        }
    }

    @Override
    public boolean supports(String paymentStatus) {
        return "TRADE_SUCCESS".equals(paymentStatus);
    }

    //------------------------------------------------------------私有方法----------------------------------------------
    /**
     * 判断订单是否已处理过的方法
     * @param orderId
     * @return {@link boolean}
     * @Author: 李良杰
     * @Date：2024/4/22 11:21
     */
    private boolean isOrderProcessed(String orderId) {
        // 这里可以根据订单号查询数据库或者其他持久化存储，判断订单是否已经处理过
        // 如果订单已处理，则返回 true；否则返回 false
        // 示例中直接返回 false，表示订单未处理过
        return false;
    }

}
