package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.InvoiceResponse;
import com.elderdev.orderFlow.entity.Invoice;
import com.elderdev.orderFlow.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(invoiceService.findById(id)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<InvoiceResponse> selectByOrderId(@PathVariable Long orderId) {
      return ResponseEntity.ok(toResponse(invoiceService.findByOrderId(orderId)));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<InvoiceResponse> pay(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(invoiceService.markAsPaid(id)));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        invoiceService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    private InvoiceResponse toResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                invoice.getIssuedAt(),
                invoice.getOrder().getId()
        );
    }
}
