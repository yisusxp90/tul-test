package com.tul.test.ecommerce.controller;

import com.tul.test.ecommerce.model.Order;
import com.tul.test.ecommerce.model.OrderProduct;
import com.tul.test.ecommerce.service.IOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/apirest/orderLine")
@Api(value="OrderLineResource", description="Controller that handles Orders requests")
public class OrderController {

    private final IOrderService iOrderService;

    @Autowired
    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createOrderLine(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.save(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderProducts(@PathVariable Integer id) {
        return ResponseEntity.ok().body(iOrderService.findAllProducts(id));
    }

    @PutMapping("/add-products/{id}")
    public ResponseEntity<?> addProductsToOrder(@PathVariable Integer id, @RequestBody List<OrderProduct> orderProducts){
        Optional<Order> o = iOrderService.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }
        Order orderLine = o.get();
        orderProducts.forEach(orderProduct -> {
            orderLine.addOrderProduct(orderProduct);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.save(orderLine));
    }

    @PutMapping("/delete-products/{id}")
    public ResponseEntity<?> removeProductsToOrder(@PathVariable Integer id, @RequestBody List<OrderProduct> orderProducts){
        Optional<Order> o = iOrderService.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }
        Order orderLine = o.get();
        orderProducts.forEach(orderProduct -> {
            orderLine.removeOrderProduct(orderProduct);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.save(orderLine));
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody Order order, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.updateOrder(order, id));
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<?> checkout(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.checkout(id));
    }

}
