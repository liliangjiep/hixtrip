package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author : 李良杰
 * @Description :
 * @Date : 2024/4/17 21:29
 * version :1.0
 **/
@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.toDataObject(order);
        orderMapper.insertOrder(orderDO);
    }

    @Override
    public int updatePayStatus(Map<String, Object> params) {
        return orderMapper.updatePayStatus(params);
    }
}
