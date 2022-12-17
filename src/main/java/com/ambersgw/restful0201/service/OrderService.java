package com.ambersgw.restful0201.service;

import com.ambersgw.restful0201.dto.CreateOrderRequest;
import com.ambersgw.restful0201.dto.OrderQueryParams;
import com.ambersgw.restful0201.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
