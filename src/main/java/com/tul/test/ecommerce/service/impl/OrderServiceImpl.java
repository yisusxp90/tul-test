package com.tul.test.ecommerce.service.impl;

import com.tul.test.ecommerce.exceptions.EcommerceBusinessException;
import com.tul.test.ecommerce.model.Order;
import com.tul.test.ecommerce.model.OrderProduct;
import com.tul.test.ecommerce.model.States;
import com.tul.test.ecommerce.repository.IOrderRepository;
import com.tul.test.ecommerce.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository iOrderLineRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    public OrderServiceImpl(IOrderRepository iOrderLineRepository) {
        this.iOrderLineRepository = iOrderLineRepository;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        order.setState(States.PENDING.getValue());
        return iOrderLineRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Integer id) {
        return iOrderLineRepository.findById(id);
    }

    @Override
    public List<OrderProduct> findAllProducts(Integer id) {
        Optional<Order> orderOptional =  iOrderLineRepository.findById(id);
        if (!orderOptional.isPresent()) {
            logger.info("------method: findAllProducts, Order doesn't exist-------");
            throw new EcommerceBusinessException("Order doesn't exist", HttpStatus.NOT_FOUND.value());
        }
        return orderOptional.get().getOrderProducts();
    }

    @Override
    public Order updateOrder(Order order, Integer id) {
        Optional<Order> orderOptional =  iOrderLineRepository.findById(id);
        if (!orderOptional.isPresent()) {
            logger.info("------method: updateOrder, Order doesn't exist-------");
            throw new EcommerceBusinessException("Order doesn't exist", HttpStatus.NOT_FOUND.value());
        }
        Order orderUpdate = orderOptional.get();
        if (orderUpdate.getState().equals(States.COMPLETE.getValue())) {
            logger.info("------method: updateOrder, Order completed-------");
            throw new EcommerceBusinessException("Order is already completed", HttpStatus.BAD_REQUEST.value());
        }
        orderUpdate.getOrderProducts().forEach(orderProductUpdate -> {
            order.getOrderProducts().forEach(orderProduct -> {
                if (orderProductUpdate.getId().equals(orderProduct.getId())) {
                    orderProductUpdate.setQuantity(orderProduct.getQuantity());
                }
            });
        });
        return iOrderLineRepository.save(orderUpdate);
    }

    @Override
    public Order checkout(Integer id) {
        Optional<Order> orderOptional =  iOrderLineRepository.findById(id);
        if (!orderOptional.isPresent()) {
            logger.info("------method: checkout, Order doesn't exist-------");
            throw new EcommerceBusinessException("Order doesn't exist", HttpStatus.NOT_FOUND.value());
        }
        Order order = orderOptional.get();
        float total = 0;
        for (OrderProduct orderProduct: order.getOrderProducts()){
            total += orderProduct.getQuantity() * orderProduct.getProduct().getPrice();
        }
        order.setTotal(total);
        order.setState(States.COMPLETE.getValue());
        return iOrderLineRepository.save(order);
    }
}
