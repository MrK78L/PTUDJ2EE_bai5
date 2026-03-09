package com.example.PTUDJ2EE_bai5.controller;

import com.example.PTUDJ2EE_bai5.model.Product;
import com.example.PTUDJ2EE_bai5.service.CategoryService;
import com.example.PTUDJ2EE_bai5.service.ProductService;
import com.example.PTUDJ2EE_bai5.util.ImageUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, 
                            BindingResult result, 
                            @RequestParam("uploadFile") MultipartFile uploadFile,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return product.getId() != null ? "product/edit" : "product/add";
        }

        // Handle file upload
        if (uploadFile != null && !uploadFile.isEmpty()) {
            try {
                String filename = imageUploadUtil.uploadImage(uploadFile);
                product.setImageFileName(filename);
            } catch (IOException e) {
                model.addAttribute("error", "Error uploading image: " + e.getMessage());
                model.addAttribute("categories", categoryService.getAllCategories());
                return product.getId() != null ? "product/edit" : "product/add";
            }
        }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product != null && product.getImageFileName() != null && !product.getImageFileName().isEmpty()) {
            try {
                imageUploadUtil.deleteImage(product.getImageFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

