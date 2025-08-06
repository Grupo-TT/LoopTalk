package com.alura.looptalk.domain.topico.service;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.dto.ActualizarTopico;
import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.topico.dto.RegistroTopico;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import com.alura.looptalk.domain.topico.service.validacion.ValidadorTopico;
import com.alura.looptalk.domain.usuario.Usuario;
import com.alura.looptalk.domain.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorTopico> validadores;


    public DetalleTopico registrar(RegistroTopico datos, Usuario autor) {
        validadores.forEach(v -> v.validar(datos));
        var curso = cursoRepository.getReferenceById(datos.idCurso());

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);

        return new DetalleTopico(topico);
    }

    public DetalleTopico actualizar(Long id, ActualizarTopico datos, Usuario actual) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        if (!topico.getAutor().getId().equals(actual.getId())) {
            throw new SecurityException("No tienes permiso para actualizar este tópico.");
        }
        Curso curso = null;
        if (topicoRepository.existsById(datos.idCurso())) {
            curso = cursoRepository.findById(datos.idCurso())
                    .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        }

        topico.actualizarDatos(datos.titulo(), datos.mensaje(), datos.estado(), curso);
        return new DetalleTopico(topico);
    }

    public void remover(Long id, Usuario actual) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topico no encontrado"));

        boolean esAutor = topico.getAutor().getId().equals(actual.getId());
        boolean esProfesorOModerador = actual.getRol().name().equals("PROFESOR") || actual.getRol().name().equals("MODERADOR");

        if (!esAutor && !esProfesorOModerador) {
            throw new SecurityException("No tienes permiso para eliminar este topico.");
        }

        topicoRepository.deleteById(id);
    }
}
