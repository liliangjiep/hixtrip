package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayCallbackStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : 李良杰
 * @Description :支付回调执行器类，用于根据支付状态执行对应的支付回调策略。
 * @Date : 2024/4/18 10:09
 * version :1.0
 **/
@Component
public class PaymentCallbackDomainService {

    @Autowired
    private List<PayCallbackStrategy> strategyList;

    public void executeStrategy(String paymentStatus, CommandPay commandPay) {
        for (PayCallbackStrategy strategy : strategyList) {
            if (strategy.supports(paymentStatus)) {
                strategy.handlePayCallback(commandPay);
                return;
            }
        }
        // 没有匹配的策略
        throw new UnsupportedOperationException("Unsupported payment status: " + paymentStatus);
    }

}
