package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentCallbackService;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.PaymentCallbackDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author : 李良杰
 * @Description :付回调服务，根据具体的支付回调策略来处理支付回调通知。
 * @Date : 2024/4/17 23:59
 * version :1.0
 **/
@Component
public class PaymentCallbackServiceImpl implements PaymentCallbackService {
    @Autowired
    private PaymentCallbackDomainService paymentCallbackDomainService;

    /**
     *
     * 处理支付回调通知
     *
     * @param commandPayDTO 支付回调通知的入参对象
     * @return 处理结果，通常为一个字符串表示处理结果或状态
     * @Author: 李良杰
     * @Date：2024/4/17 23:59
     */
    @Override
    public void handlePayCallback(CommandPayDTO commandPayDTO) {
        String paymentStatus = commandPayDTO.getPayStatus();
        CommandPay commandPay = PayConvertor.INSTANCE.buildCommandPay(commandPayDTO);
        // 根据支付状态选择相应的策略进行处理
        paymentCallbackDomainService.executeStrategy(paymentStatus, commandPay);
    }
}
