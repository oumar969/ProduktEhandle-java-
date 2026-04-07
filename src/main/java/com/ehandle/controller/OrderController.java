package com.ehandle.controller;

import com.ehandle.model.Order;
import com.ehandle.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "Get user orders", description = "Retrieve all orders for the authenticated user")
    public ResponseEntity<Page<Order>> getUserOrders(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Getting orders for user: {}", authentication.getName());
        Page<Order> orders = orderService.getUserOrders(authentication.getName(), pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
