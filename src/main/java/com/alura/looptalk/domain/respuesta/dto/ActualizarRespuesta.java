package com.alura.looptalk.domain.respuesta.dto;

import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;

import java.time.LocalDateTime;

public record ActualizarRespuesta(
        Long id,
        String mensaje,
        Boolean solucion
) {
}
