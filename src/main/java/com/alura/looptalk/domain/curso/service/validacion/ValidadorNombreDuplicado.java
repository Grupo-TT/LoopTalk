package com.alura.looptalk.domain.curso.service.validacion;

import com.alura.looptalk.domain.curso.dto.RegistroCurso;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNombreDuplicado implements ValidadorCurso {

    @Autowired
    private CursoRepository cursoRepository;

    public void validar(RegistroCurso datos) {
        if (cursoRepository.existsByNombre(datos.nombre())) {
            throw new ValidacionException("Ya existe un Curso con el mismo título.");
        }
    }
}
