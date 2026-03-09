package com.example.PTUDJ2EE_bai5.repository;

import com.example.PTUDJ2EE_bai5.model.Product;
import com.example.PTUDJ2EE_bai5.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByCategory(Category category);
}
