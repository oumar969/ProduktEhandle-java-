package com.ehandle.service;

import com.ehandle.exception.ResourceNotFoundException;
import com.ehandle.model.Order;
import com.ehandle.model.User;
import com.ehandle.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Page<Order> getUserOrders(String username, Pageable pageable) {
        User user = userService.getUserByUsername(username);
        return orderRepository.findByUser_Id(user.getId(), pageable);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
