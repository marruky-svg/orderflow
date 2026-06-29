package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Customer;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.CustomerRepository;
import com.elderdev.orderFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public Customer findByUserID(Long userId){
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Costumer not found"));
    }

    public Customer create(Long userId, Customer customer) {

            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            customer.setUser(user);
            return customerRepository.save(customer);

    }

    public Customer update(Long id, Customer customer) {
        var existing = findById(id);
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());
        return customerRepository.save(existing);
    }

    public void delete(Long id) {
        if (customerRepository.existsById(id)) {
           customerRepository.deleteById(id);
           return;
        }
        throw new NotFoundException("Customer not found");
    }


}
