package com.ecommerce.enanpharma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Long price;
    private Integer quantity;
    private String imageUrl;

    public ProductDTO(Long id, String name, String description, Long price, Long categoryId, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public ProductDTO(Long id, String name, String description, Long price, Long categoryId, Integer quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public ProductDTO() {
        
    }
}
