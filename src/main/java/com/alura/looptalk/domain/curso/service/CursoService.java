package com.alura.looptalk.domain.curso.service;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.curso.dto.ActualizarCurso;
import com.alura.looptalk.domain.curso.dto.DetalleCurso;
import com.alura.looptalk.domain.curso.dto.RegistroCurso;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.domain.curso.service.validacion.ValidadorCurso;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorCurso> validadores;

    public DetalleCurso registrar(RegistroCurso datos) {
        validadores.forEach(v -> v.validar(datos));

        var curso = new Curso(datos);
        cursoRepository.save(curso);

        return new DetalleCurso(curso);
    }

    public DetalleCurso actualizar(Long id, ActualizarCurso datos) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        curso.actualizarDatos(datos);
        return new DetalleCurso(curso);
    }

    public void remover(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new EntityNotFoundException("Curso no encontrado");
        }
        cursoRepository.deleteById(id);
    }


}
