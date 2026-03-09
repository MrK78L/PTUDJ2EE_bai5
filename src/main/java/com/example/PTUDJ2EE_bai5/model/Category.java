package com.example.PTUDJ2EE_bai5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên danh mục là bắt buộc")
    @Column(nullable = false, length = 255)
    private String name;
}
