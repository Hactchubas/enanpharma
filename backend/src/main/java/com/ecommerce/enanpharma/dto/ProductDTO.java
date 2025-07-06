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

    public ProductDTO(Long id, String name, String description, Long price, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductDTO() {
        
    }
}
