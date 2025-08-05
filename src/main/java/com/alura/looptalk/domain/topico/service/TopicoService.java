package com.alura.looptalk.domain.topico.service;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.dto.ActualizarTopico;
import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.topico.dto.RegistroTopico;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import com.alura.looptalk.domain.topico.service.validacion.ValidadorTopico;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorTopico> validadores;


    public DetalleTopico registrar(RegistroTopico datos) {
        validadores.forEach(v -> v.validar(datos));

        var autor = usuarioRepository.getReferenceById(datos.idAutor());
        var curso = cursoRepository.getReferenceById(datos.idCurso());

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        topicoRepository.save(topico);

        return new DetalleTopico(topico);
    }

    public DetalleTopico actualizar(Long id, ActualizarTopico datos) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        Curso curso = null;
        if (topicoRepository.existsById(datos.idCurso())) {
            curso = cursoRepository.findById(datos.idCurso())
                    .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        }

        topico.actualizarDatos(datos.titulo(), datos.mensaje(), datos.estado(), curso);
        return new DetalleTopico(topico);
    }

    public void remover(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }
}
