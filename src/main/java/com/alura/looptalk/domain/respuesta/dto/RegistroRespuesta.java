package com.alura.looptalk.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroRespuesta(
        @NotBlank String mensaje,
        @NotNull Long idAutor
) {
}
