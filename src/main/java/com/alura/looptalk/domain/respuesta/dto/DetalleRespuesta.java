package com.alura.looptalk.domain.respuesta.dto;

import com.alura.looptalk.domain.curso.dto.DetalleCurso;
import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;

import java.time.LocalDateTime;

public record DetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        DetalleTopico topico,
        DetalleUsuario autor
) {
    public DetalleRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                new DetalleTopico(respuesta.getTopico()),
                new DetalleUsuario(respuesta.getAutor())
        );
    }
}
