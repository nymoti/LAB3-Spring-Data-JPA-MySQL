package com.example.lab3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(
        Long id,

        @NotBlank(message = "Le nom est obligatoire")
        String name,

        @NotNull(message = "Le prix est obligatoire")
        @Positive(message = "Le prix doit être positif")
        BigDecimal price,

        @NotNull(message = "La catégorie est obligatoire")
        Long categoryId,

        String categoryName
) {
}
