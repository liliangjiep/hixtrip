package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author : 貔貅
 * @Description :付回调服务，根据具体的支付回调策略来处理支付回调通知。
 * @Date : 2024/4/17 23:55
 * version :1.0
 **/
public interface payService {
    //@Autowired
    //private PaymentCallbackStrategy paymentCallbackStrategy;

    ///**
    // * 处理支付回调通知
    // *
    // * @param commandPayDTO 支付回调通知的入参对象
    // * @return 处理结果，通常为一个字符串表示处理结果或状态
    // */
    //public String handlePaymentCallback(CommandPayDTO commandPayDTO) {
    //    // 调用具体的支付回调策略来处理支付回调
    //    return paymentCallbackStrategy.handlePaymentCallback(commandPayDTO);
    //}

    /**
     * /**
     * 处理支付回调通知
     *
     * @param commandPayDTO 支付回调通知的入参对象
     * @return 处理结果，通常为一个字符串表示处理结果或状态
     * @Author: 貔貅
     * @Date：2024/4/17 23:59
     */
    void executeStrategy(String paymentStatus, CommandPayDTO commandPayDTO);

}
