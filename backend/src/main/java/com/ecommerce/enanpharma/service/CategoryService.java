package com.ecommerce.enanpharma.service;

import com.ecommerce.enanpharma.dto.CategoryDTO;
import com.ecommerce.enanpharma.entity.Category;
import com.ecommerce.enanpharma.exception.ResourceNotFoundException;
import com.ecommerce.enanpharma.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        return repository.findAll().stream()
                .map( category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        return repository.findById(id)
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());
        Category savedCategory = repository.save(category);
        return new CategoryDTO(savedCategory.getId(), category.getName());
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        return repository.findById(id).map(cat -> {
            cat.setName(categoryDTO.getName());
            Category updated = repository.save(cat);
            return new CategoryDTO(updated.getId(), updated.getName());
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
        return true;
    }
}
