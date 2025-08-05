package com.alura.looptalk.domain.topico.service.validacion;

import com.alura.looptalk.domain.topico.dto.RegistroTopico;
import com.alura.looptalk.infra.exceptions.ValidacionException;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTituloYMensajeDuplicado implements ValidadorTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(RegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje.");
        }
    }
}