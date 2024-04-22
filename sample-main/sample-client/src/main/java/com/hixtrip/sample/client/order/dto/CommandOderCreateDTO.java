package com.hixtrip.sample.client.order.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 创建订单的请求 入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandOderCreateDTO {

    /**
     * 商品规格id
     */
    @NotNull(message = "skuId不能为空")
    private String skuId;

    /**
     * 购买数量
     */
    @Min(value = 1, message = "购买数量不能小于1")
    private Integer amount;

    /**
     * 用户id
     */
    @NotNull(message = "userId不能为空")
    private String userId;


}
