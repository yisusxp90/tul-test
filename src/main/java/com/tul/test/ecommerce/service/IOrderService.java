package com.tul.test.ecommerce.service;

import com.tul.test.ecommerce.model.Order;
import com.tul.test.ecommerce.model.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Order save(Order order);
    Optional<Order> findById(Integer id);
    List<OrderProduct> findAllProducts(Integer id);
    Order updateOrder(Order order, Integer id);
    Order checkout(Integer id);
}
