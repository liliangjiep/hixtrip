package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author : 李良杰
 * @Description :支付失败策略
 * @Date : 2024/4/18 21:30
 * version :1.0
 **/
@Component
public class PayFailStrategy implements PayCallbackStrategy {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
        //记录支付回调结果
        public void handlePayCallback(CommandPay commandPay) {
        //调用领域服务处理订单支付失败
        orderDomainService.orderPayFail(commandPay);
        payDomainService.payRecord(commandPay);
        //查询订单，获取订单数量和skuId 代码省略
        //调用扣减库存方法 支付失败：增加可售库存，减少预占库存，占用库存不变
        Long amout = 200L;
        String skuId = "123";
        inventoryDomainService.changeInventory(skuId, amout, -amout, 0L);
    }

    @Override
    public boolean supports(String paymentStatus) {
        return "TRADE_CLOSED ".equals(paymentStatus);
    }
}
