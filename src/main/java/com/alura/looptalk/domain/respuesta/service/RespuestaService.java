package com.alura.looptalk.domain.respuesta.service;

import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.respuesta.dto.ActualizarRespuesta;
import com.alura.looptalk.domain.respuesta.dto.DetalleRespuesta;
import com.alura.looptalk.domain.respuesta.dto.RegistroRespuesta;
import com.alura.looptalk.domain.respuesta.repository.RespuestaRepository;
import com.alura.looptalk.domain.topico.StatusTopico;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import com.alura.looptalk.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;


    public DetalleRespuesta registrar(RegistroRespuesta datos, Long idTopico, Usuario autor) {

        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        var respuesta = new Respuesta(datos, topico, autor);
        respuestaRepository.save(respuesta);

        if (topico.getStatus() == StatusTopico.NO_RESPONDIDO) {
            topico.actualizarEstado(StatusTopico.NO_SOLUCIONADO);
        }
        return new DetalleRespuesta(respuesta);
    }

    public DetalleRespuesta actualizar(Long id, ActualizarRespuesta datos, Usuario actual) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        if (!respuesta.getAutor().getId().equals(actual.getId())) {
            throw new SecurityException("No tienes permiso para actualizar esta respuesta.");
        }

        respuesta.actualizarDatos(datos);
        return new DetalleRespuesta(respuesta);
    }


    @Transactional
    public void marcarSolucion(Long id, Usuario actual) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        Topico topico = respuesta.getTopico();

        boolean esAutorTopico = topico.getAutor().getId().equals(actual.getId());
        boolean esProfesorOModerador = actual.getRol().name().equals("PROFESOR") || actual.getRol().name().equals("MODERADOR");

        if (!esAutorTopico && !esProfesorOModerador) {
            throw new SecurityException("No tienes permiso para marcar esta respuesta como solución.");
        }

        respuesta.marcarSolucion();

        if (respuesta.getSolucion()) {
            topico.actualizarEstado(StatusTopico.SOLUCIONADO);
        } else {
            boolean otraSolucion = respuestaRepository.existsByTopicoIdAndSolucionTrue(topico.getId());
            topico.actualizarEstado(otraSolucion ? StatusTopico.SOLUCIONADO : StatusTopico.NO_SOLUCIONADO);
        }
    }


    @Transactional
    public void remover(Long id, Usuario actual) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        boolean esAutor = respuesta.getAutor().getId().equals(actual.getId());
        boolean esProfesorOModerador = actual.getRol().name().equals("PROFESOR") || actual.getRol().name().equals("MODERADOR");

        if (!esAutor && !esProfesorOModerador) {
            throw new SecurityException("No tienes permiso para eliminar esta respuesta.");
        }

        Topico topico = respuesta.getTopico();
        boolean eraSolucion = respuesta.getSolucion();
        respuestaRepository.delete(respuesta);

        long totalRespuestas = respuestaRepository.countByTopicoId(topico.getId());
        if (totalRespuestas == 0) {
            topico.actualizarEstado(StatusTopico.NO_RESPONDIDO);
        } else if (eraSolucion) {
            boolean hayOtraSolucion = respuestaRepository.existsByTopicoIdAndSolucionTrue(topico.getId());
            topico.actualizarEstado(hayOtraSolucion ? StatusTopico.SOLUCIONADO : StatusTopico.NO_SOLUCIONADO);
        }
    }




}
