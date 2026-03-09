package com.example.PTUDJ2EE_bai5.service;

import com.example.PTUDJ2EE_bai5.model.Category;
import com.example.PTUDJ2EE_bai5.repository.CategoryRepository;
import com.example.PTUDJ2EE_bai5.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Map<String, Object>> getAllCategoriesWithProductCount() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            Map<String, Object> map = new HashMap<>();
            map.put("category", category);
            map.put("productCount", productRepository.countByCategory(category));
            map.put("canDelete", productRepository.countByCategory(category) == 0);
            return map;
        }).collect(Collectors.toList());
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public boolean canDeleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return false;
        }
        return productRepository.countByCategory(category) == 0;
    }
}
