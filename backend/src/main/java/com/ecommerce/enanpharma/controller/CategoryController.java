package com.ecommerce.enanpharma.controller;

import com.ecommerce.enanpharma.dto.CategoryDTO;
import com.ecommerce.enanpharma.repository.CategoryRepository;
import com.ecommerce.enanpharma.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
class CategoryController {
    private final CategoryService service;

    CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return  service.findById(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        return service.create(categoryDTO);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return service.update(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
     public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

}
