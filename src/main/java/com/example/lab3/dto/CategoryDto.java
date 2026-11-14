package com.example.lab3.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        Long id,

        @NotBlank(message = "Le nom est obligatoire")
        String name
) {
}
