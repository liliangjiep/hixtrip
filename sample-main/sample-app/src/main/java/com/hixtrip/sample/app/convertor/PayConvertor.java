package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author : 李良杰
 * @Description :
 * @Date : 2024/4/18 18:42
 * version :1.0
 **/
@Mapper
public interface PayConvertor {
    PayConvertor INSTANCE = Mappers.getMapper(PayConvertor.class);

    CommandPay buildCommandPay(CommandPayDTO commandPayDTO);
}
