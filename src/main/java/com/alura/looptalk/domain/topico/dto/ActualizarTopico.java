package com.alura.looptalk.domain.topico.dto;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.curso.dto.DetalleCurso;
import com.alura.looptalk.domain.topico.StatusTopico;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopico(
        Long id,
        String titulo,
        String mensaje,
        StatusTopico estado,
        Long idCurso) {
}
