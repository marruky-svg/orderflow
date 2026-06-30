package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.ProductRequest;
import com.elderdev.orderFlow.dto.ProductResponse;
import com.elderdev.orderFlow.entity.Product;
import com.elderdev.orderFlow.service.CategoryService;
import com.elderdev.orderFlow.service.ProductService;
import com.elderdev.orderFlow.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> selectAll() {
        return ResponseEntity.ok(productService.findAll()
                .stream()
                .map(this::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(productService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create( @Valid @RequestBody ProductRequest request) {
        var product = toProduct(request);
        productService.create(product);
        return ResponseEntity.status(201).body(toResponse(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        var updated = productService.update(id, toProduct(request));
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse
                (
                      product.getId(),
                      product.getName(),
                      product.getDescription(),
                      product.getPrice(),
                      product.getStockQuantity(),
                      product.getCreatedAt(),
                      product.getCategory().getName(),
                      product.getSupplier().getName()
                );
    }

    private Product toProduct(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .category(categoryService.findById(request.categoryId()))
                .supplier(supplierService.findById(request.supplierId()))
                .build();
    }
}
