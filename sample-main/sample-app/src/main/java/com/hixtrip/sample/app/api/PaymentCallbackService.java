package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * @Author : 李良杰
 * @Description :支付回调服务，根据具体的支付回调策略来处理支付回调通知。
 * @Date : 2024/4/17 23:55
 * version :1.0
 **/
public interface PaymentCallbackService {

    /**
     * /**
     * 处理支付回调通知
     *
     * @param commandPayDTO 支付回调通知的入参对象
     * @return 处理结果，通常为一个字符串表示处理结果或状态
     * @Author: 李良杰
     * @Date：2024/4/17 23:59
     */
    void handlePayCallback( CommandPayDTO commandPayDTO);

}
