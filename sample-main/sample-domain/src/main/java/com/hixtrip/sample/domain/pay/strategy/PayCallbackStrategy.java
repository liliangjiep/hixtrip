package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @Author : 李良杰
 * @Description :支付回调策略接口
 * @Date : 2024/4/18 18:46
 * version :1.0
 **/
public interface PayCallbackStrategy {
    void handlePayCallback(CommandPay commandPay);
    boolean supports(String paymentStatus);
}
