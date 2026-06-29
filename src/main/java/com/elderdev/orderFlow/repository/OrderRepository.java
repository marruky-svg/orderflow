package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Customer;
import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(OrderStatus status);
}
