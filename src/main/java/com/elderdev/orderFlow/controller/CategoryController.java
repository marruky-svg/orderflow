package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.CategoryRequest;
import com.elderdev.orderFlow.dto.CategoryResponse;
import com.elderdev.orderFlow.entity.Category;
import com.elderdev.orderFlow.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> selectAll(){
        List<CategoryResponse> response = new ArrayList<>();
        var categories = categoryService.findAll();
        for(var category : categories) {
            response.add(toResponse(category));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> selectById(@PathVariable Long id){
        var category = categoryService.findById(id);
        return ResponseEntity.ok(toResponse(category));
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        var created = categoryService.create(category);
        return ResponseEntity.status(201).body(toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        var updated = categoryService.update(id, category);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreateAt()
        );
    }
}
