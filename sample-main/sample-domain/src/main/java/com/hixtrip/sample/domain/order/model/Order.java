package com.hixtrip.sample.domain.order.model;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;


    /**
     * SkuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建订单
     * @param userId 购买人
     * @param skuId SkuId
     * @param amount 购买数量
     * @param money 购买金额
     * @return 创建的订单
     */

    /**
     * 使用雪花算法生成订单号
     */
    public static String generateOrderId() {
        // 在这里通过hutool调用雪花算法生成订单号的逻辑
        // 创建一个雪花算法ID生成器，默认workerId为0，datacenterId为0
        Snowflake snowflake = IdUtil.getSnowflake(0, 0);
        // 生成一个雪花算法ID
        return snowflake.nextIdStr();
    }


    /**
     * 创建待支付状态的订单
     * @param userId
     * @param skuId
     * @param amount
     * @param money
     * @return {@link Order}
     * @Author: 李良杰
     * @Date：2024/4/13 22:02
     */
    public static Order createOrder(String userId, String skuId, Integer amount, BigDecimal money) {
        return Order.builder()
                .id(generateOrderId()) // 使用雪花算法生成订单号
                .userId(userId)
                .skuId(skuId)
                .amount(amount)
                .money(money)
                .payTime(null) // 待支付状态，支付时间为空
                .payStatus("WAITING_FOR_PAYMENT") // 待支付状态
                .delFlag(0L) // 删除标志，默认为0
                .createTime(LocalDateTime.now()) // 创建时间
                .createBy("test")
                .updateTime(null) // 修改时间初始为空
                .build();
    }
}
