package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author : 貔貅
 * @Description :
 * @Date : 2024/4/18 10:09
 * version :1.0
 **/
@Component
public class PaymentCallbackDomainService {
    @Autowired
    private Map<String, PayCallbackStrategy> strategyMap;

    public void executeStrategy(String paymentStatus, CommandPay commandPay) {
        PayCallbackStrategy strategy = strategyMap.get(paymentStatus);
        if (strategy != null) {
            strategy.handlePayCallback(commandPay);
        } else {
            // 没有匹配的策略
            throw new UnsupportedOperationException("Unsupported payment status: " + paymentStatus);
        }
    }
}
