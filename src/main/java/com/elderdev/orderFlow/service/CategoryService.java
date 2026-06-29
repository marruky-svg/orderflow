package com.elderdev.orderFlow.service;


import com.elderdev.orderFlow.entity.Category;
import com.elderdev.orderFlow.exception.AlreadyExistsException;
import com.elderdev.orderFlow.exception.NotFoundException;
import com.elderdev.orderFlow.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category wasn't found"));
    }

    public Category create(Category category) {
       if(categoryRepository.existsByName(category.getName())){
           throw new AlreadyExistsException("This category name already exists");
       }
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category){
        var existing = findById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(Long id){
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }else{
            throw new NotFoundException("Id not found");
        }
    }
}
