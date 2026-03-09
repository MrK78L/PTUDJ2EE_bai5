package com.example.PTUDJ2EE_bai5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm là bắt buộc")
    @Column(nullable = false, length = 255)
    private String name;

    @NotNull(message = "Giá là bắt buộc")
    @Min(value = 1, message = "Giá phải lớn hơn 0")
    @Max(value = 9999999, message = "Giá không được vượt quá 9.999.999")
    @Column(nullable = false)
    private Double price;

    @Column(length = 255)
    private String image;

    @Column(length = 255)
    private String imageFileName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
