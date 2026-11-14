package com.example.lab3.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,

        LocalDateTime orderDate,

        @NotEmpty(message = "La commande doit contenir au moins un article")
        List<@Valid OrderItemDto> items
) {
}
