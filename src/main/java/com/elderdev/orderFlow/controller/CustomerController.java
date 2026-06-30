package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.CustomerRequest;
import com.elderdev.orderFlow.dto.CustomerResponse;
import com.elderdev.orderFlow.entity.Customer;
import com.elderdev.orderFlow.service.CustomerService;
import com.elderdev.orderFlow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> selectAll() {
        return ResponseEntity.ok(customerService.findAll().stream()
                .map(this::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok().body(toResponse(customerService.findById(id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<CustomerResponse> create(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        var customer = toCustomer(request);
        return ResponseEntity.status(201).body(toResponse(customerService.create(id, customer)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        var customer = customerService.findById(id);
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setPhone(request.phone());
        customer.setAddress(request.address());
        return ResponseEntity.ok(toResponse(customerService.update(id, customer)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCreatedAt(),
                customer.getUser().getUsername()
        );
    }

    private Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .address(request.address())
                .build();
    }
}
