package com.example.lab3.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDto(
        Long id,

        @NotNull(message = "Le produit est obligatoire")
        Long productId,

        String productName,

        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être positive")
        Integer quantity
) {
}
