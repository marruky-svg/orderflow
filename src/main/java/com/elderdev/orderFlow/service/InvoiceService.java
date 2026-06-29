package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Invoice;
import com.elderdev.orderFlow.entity.InvoiceStatus;
import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found"));
    }

    public Invoice findByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Invoice not found"));
    }

    public Invoice generate(Order order){
        int year = LocalDateTime.now().getYear();
        var beginningYear = LocalDateTime.of(year,1,1,0,0);
        var endYear = LocalDateTime.of(year, 12,31,23,59);
        Long count = invoiceRepository.countByCreatedAtBetween(beginningYear, endYear);
        String invoiceNumber = String.format("INV-%d-%04d", year, count + 1);
        Invoice invoice = Invoice.builder()
                .order(order)
                .invoiceNumber(invoiceNumber)
                .totalAmount(order.getTotalAmount())
                .status(InvoiceStatus.PENDING)
                .build();
        return invoiceRepository.save(invoice);
    }

    public Invoice markAsPaid(Long id) {
        var invoice = findById(id);
        invoice.setStatus(InvoiceStatus.PAID);
        return invoiceRepository.save(invoice);
    }

    public void cancel(Long id) {
        var invoice = findById(id);
        if(invoice.getStatus().equals(InvoiceStatus.PAID)){
            throw new IllegalArgumentException("Invoice already paid");
        }
        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoiceRepository.save(invoice);
    }
}
