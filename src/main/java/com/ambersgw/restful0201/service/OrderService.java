package com.ambersgw.restful0201.service;

import com.ambersgw.restful0201.dto.CreateOrderRequest;
import com.ambersgw.restful0201.model.Order;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
