package com.alura.looptalk.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroRespuesta(
        @NotBlank String mensaje
) {
}
