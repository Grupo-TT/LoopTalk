package com.alura.looptalk.domain.curso.dto;

import com.alura.looptalk.domain.curso.Curso;

public record DetalleCurso(
        Long id,
        String nombre,
        String categoria) {

    public DetalleCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }
}
