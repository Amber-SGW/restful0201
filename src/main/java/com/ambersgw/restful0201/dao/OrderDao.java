package com.ambersgw.restful0201.dao;

import com.ambersgw.restful0201.dto.OrderQueryParams;
import com.ambersgw.restful0201.model.Order;
import com.ambersgw.restful0201.model.OrderItem;

import java.util.List;

public interface OrderDao {

  List<Order> getOrders(OrderQueryParams orderQueryParams);

  Integer countOrder(OrderQueryParams orderQueryParams);

  List<OrderItem> getOrderItemByOrderId(Integer orderId);

  Order getOrderById(Integer orderId);

  Integer createOrder(Integer userId, Integer totalAmount);
  void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
