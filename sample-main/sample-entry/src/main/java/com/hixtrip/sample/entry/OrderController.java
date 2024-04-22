package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PaymentCallbackService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentCallbackService paymentCallbackService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return ResponseEntity<String>
     */
    @PostMapping(path = "/command/order/create")
    public ResponseEntity<String>  order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟
        // 模拟登录信息 并判断id和登录的ID是否一致
        // User user = userService.getUserByUserId(userId);
        // if (null == user) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user ID.");
        // }
        //创建订单并进行库存扣减。
        //
        orderService.createOrder(commandOderCreateDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.OK);
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public ResponseEntity<String> payCallback(@RequestBody CommandPayDTO commandPayDTO) {
         //这里采取支付宝的回调状态进行设计的策略模式
        paymentCallbackService.handlePayCallback(commandPayDTO);
        return ResponseEntity.ok("Callback processed successfully.");
    }

}
