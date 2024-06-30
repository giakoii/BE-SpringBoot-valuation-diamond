package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.OrderDTO;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.service.OrderServiceImp;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order_request")
public class OrderController {
    @Autowired
    private OrderServiceImp orderServiceImp;

    // =================================== API CREATE ======================================

    @PostMapping("/create")
    Order createOrder(@RequestBody OrderDTO orderDTO) {
        return orderServiceImp.saveOrder(orderDTO);
    }

    // =================================== API GET ======================================

    @GetMapping("/getOrderByStatusInProgress")
    List<Order> getOrderByStatusInProgress() {
        return orderServiceImp.getOrders();
    }

    @GetMapping("/getOrder/{orderId}")
    Order getOrder(@PathVariable("orderId") String orderId) {

        return orderServiceImp.getOrder(orderId);
    }

    @GetMapping("/getOrders")
    List<Order> getOrders() {

        return orderServiceImp.getAllOrders();
    }

    @GetMapping("/getOrderByRequestId/{requestId}")
    List<Order> getOrderByRequestId(@PathVariable("requestId") String requestId) {
        return orderServiceImp.getOrderByRequest(requestId);

    }

    // =================================== API UPDATE ======================================

    @PutMapping("/updateStatus/{orderId}")
    public Order updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody OrderDTO orderDTO) {
        return orderServiceImp.updateOrderStatus(orderId, orderDTO);
    }

    @GetMapping("/countOrderCreated/{year}/{month}")
    public long countOrdersRegisteredWithinMonth(@PathVariable int year, @PathVariable int month) {
        return orderServiceImp.countOrdersRegisteredWithinMonth(year, month);
    }

    @GetMapping("/sumTotalPriceByOrderCreated/{year}/{month}")
    public BigDecimal sumTotalPriceByOrderCreated(@PathVariable int year, @PathVariable int month) {
        return orderServiceImp.sumTotalPriceWithinMonth(year, month);
    }
    @GetMapping("/sumQuantityWithinMonth/{year}/{month}")
    public int sumQuantityWithinMonth(@PathVariable int year, @PathVariable int month) {
        return orderServiceImp.sumQuantityWithinMonth(year, month);
    }
}
