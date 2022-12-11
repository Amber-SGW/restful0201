package com.ambersgw.restful0201.service;

import com.ambersgw.restful0201.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
