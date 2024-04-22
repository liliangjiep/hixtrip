package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

import java.util.Map;

/**
 *
 */
public interface OrderRepository {
    void createOrder(Order order);

    int updatePayStatus(Map<String, Object> params);

}
