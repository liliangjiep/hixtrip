package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author : 李良杰
 * @Description :
 * @Date : 2024/4/17 22:37
 * version :1.0
 **/
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
    // 插入订单
    void insertOrder(OrderDO order);
    //修改订单状态
    int updatePayStatus(Map<String, Object> params);
}
