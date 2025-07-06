package com.ecommerce.enanpharma.repository;

import com.ecommerce.enanpharma.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
