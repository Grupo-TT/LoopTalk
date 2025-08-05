package com.alura.looptalk.domain.topico.dto;

import com.alura.looptalk.domain.topico.StatusTopico;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.curso.dto.DetalleCurso;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;

import java.time.LocalDateTime;

public record DetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        StatusTopico estado,
        LocalDateTime fechaCreacion,
        DetalleUsuario autor,
        DetalleCurso curso) {

    public DetalleTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getFechaCreacion(),
                new DetalleUsuario(topico.getAutor()),
                new DetalleCurso(topico.getCurso())
        );
    }
}
