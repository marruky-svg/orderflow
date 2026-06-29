package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.entity.OrderItem;
import com.elderdev.orderFlow.entity.OrderStatus;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.CustomerRepository;
import com.elderdev.orderFlow.repository.OrderRepository;
import com.elderdev.orderFlow.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InvoiceService invoiceService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order updateStatus(Long id, OrderStatus status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        order.setStatus(status);
        if(order.getStatus().equals(OrderStatus.DELIVERED)) {
            invoiceService.generate(order);
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void cancel(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if(order.getStatus().equals(OrderStatus.CANCELLED)){
            throw new IllegalArgumentException("Order already cancelled");
        }

        if(order.getStatus().equals(OrderStatus.DELIVERED)){
            throw new IllegalArgumentException("Cannot cancel a delivered order");
        }


        order.setStatus(OrderStatus.CANCELLED);
        var items = order.getItems();
        for(var item : items) {
            var product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        orderRepository.save(order);
    }

    @Transactional
    public Order create(Long customerId, List<OrderItem> items, String shippingAddress) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .totalAmount(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        order = orderRepository.save(order);

        for (var item : items) {
            var product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
            item.setUnitPrice(product.getPrice());
            item.setOrder(order);
            BigDecimal itemTotal = item.getUnitPrice()
                    .multiply(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);


        }
        order.setItems(items);
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(shippingAddress);
        return orderRepository.save(order);
    }
}
