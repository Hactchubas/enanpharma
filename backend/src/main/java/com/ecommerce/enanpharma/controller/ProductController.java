package com.ecommerce.enanpharma.controller;

import com.ecommerce.enanpharma.dto.ProductDTO;
import com.ecommerce.enanpharma.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Create product
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Upload product image
    @PostMapping("/{id}/image")
    public ResponseEntity<ProductDTO> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Validate file size (5MB limit)
            long maxSize = 5 * 1024 * 1024; // 5MB in bytes
            if (file.getSize() > maxSize) {
                throw new IllegalArgumentException("File size exceeds 5MB limit. Please choose a smaller image.");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }

            // Validate file extension
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new IllegalArgumentException("Invalid file name");
            }
            
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)$")) {
                throw new IllegalArgumentException("Only JPG, PNG, GIF, and WebP images are allowed");
            }

            // Create uploads directory if it doesn't exist
            Path uploadDir = Paths.get("uploads/products");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Generate unique filename
            String filename = UUID.randomUUID().toString() + extension;
            Path filePath = uploadDir.resolve(filename);

            // Save file to filesystem
            Files.copy(file.getInputStream(), filePath);

            // Update product with image URL
            ProductDTO product = service.findById(id);
            product.setImageUrl("/uploads/products/" + filename);
            ProductDTO updated = service.update(id, product);

            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

}
