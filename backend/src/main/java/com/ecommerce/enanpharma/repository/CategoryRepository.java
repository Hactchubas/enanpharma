package com.ecommerce.enanpharma.repository;

import com.ecommerce.enanpharma.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
