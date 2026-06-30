package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.SupplierRequest;
import com.elderdev.orderFlow.dto.SupplierResponse;
import com.elderdev.orderFlow.entity.Supplier;
import com.elderdev.orderFlow.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<List<SupplierResponse>> selectAll(){
        List<SupplierResponse> response = new ArrayList<>();
        var suppliers = supplierService.findAll();
        for (var supplier : suppliers) {
            response.add(toResponse(supplier));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> selectById(@PathVariable Long id) {
         var supplier = supplierService.findById(id);
         return ResponseEntity.ok(toResponse(supplier));
    }

    @PostMapping()
    public ResponseEntity<SupplierResponse> create(@RequestBody @Valid SupplierRequest request) {
        var supplier = getSupplier(request);
        var created = supplierService.create(supplier);
        return ResponseEntity.status(201).body(toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable Long id, @RequestBody @Valid SupplierRequest request) {
        var supplier = getSupplier(request);
        var updated = supplierService.update(id, supplier);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }


    private Supplier getSupplier(SupplierRequest request) {
        var supplier = Supplier.builder()
                .name(request.name())
                .email(request.email())
                .address(request.address())
                .phone(request.phone())
                .build();
        return supplier;
    }

    private SupplierResponse toResponse(Supplier supplier){
        return new SupplierResponse
                (
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getEmail(),
                        supplier.getPhone(),
                        supplier.getAddress(),
                        supplier.getCreatedAt()
                );
    }
}
