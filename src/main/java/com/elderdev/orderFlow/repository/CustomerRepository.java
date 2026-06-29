package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Customer;
import com.elderdev.orderFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
    Optional<Customer> findByUserId(Long userId);
}
