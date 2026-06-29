package com.elderdev.orderFlow.service;

import com.elderdev.orderFlow.entity.Product;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findBySupplier(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }

    public List<Product> search(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product create(Product product){
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        var existing = findById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStockQuantity(product.getStockQuantity());
        existing.setCategory(product.getCategory());
        existing.setSupplier(product.getSupplier());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("Id doesnt exist");
    }

    public Product updateStock(Long id, Integer quantity) {
        var existing = findById(id);
        int newStock = existing.getStockQuantity() - quantity;
        if(newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        existing.setStockQuantity(newStock);
        return productRepository.save(existing);
    }
}
