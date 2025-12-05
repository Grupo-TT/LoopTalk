package com.alura.looptalk.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        String fotoUrl,
        @NotNull Long idCurso
        ) {
}
