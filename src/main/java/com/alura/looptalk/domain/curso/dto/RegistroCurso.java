package com.alura.looptalk.domain.curso.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroCurso(
        @NotBlank String nombre,
        @NotBlank String categoria
) {
}
