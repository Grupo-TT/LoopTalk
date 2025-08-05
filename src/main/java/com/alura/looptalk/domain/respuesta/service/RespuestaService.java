package com.alura.looptalk.domain.respuesta.service;

import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.respuesta.dto.ActualizarRespuesta;
import com.alura.looptalk.domain.respuesta.dto.DetalleRespuesta;
import com.alura.looptalk.domain.respuesta.dto.RegistroRespuesta;
import com.alura.looptalk.domain.respuesta.repository.RespuestaRepository;
import com.alura.looptalk.domain.topico.StatusTopico;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import com.alura.looptalk.domain.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;


    public DetalleRespuesta registrar(RegistroRespuesta datos, Long idTopico) {

        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        var autor = usuarioRepository.getReferenceById(datos.idAutor());

        var respuesta = new Respuesta(datos, topico, autor);
        respuestaRepository.save(respuesta);

        if (topico.getStatus() == StatusTopico.NO_RESPONDIDO) {
            topico.actualizarEstado(StatusTopico.NO_SOLUCIONADO);
        }
        return new DetalleRespuesta(respuesta);
    }

    public DetalleRespuesta actualizar(Long id, ActualizarRespuesta datos) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        respuesta.actualizarDatos(datos);
        return new DetalleRespuesta(respuesta);
    }

    @Transactional
    public void marcarSolucion(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));
        respuesta.marcarSolucion();

        Topico topico = respuesta.getTopico();

        if (respuesta.getSolucion()) {
            topico.actualizarEstado(StatusTopico.SOLUCIONADO);
        } else {
            boolean otraSolucion = respuestaRepository.existsByTopicoIdAndSolucionTrue(topico.getId());
            topico.actualizarEstado(otraSolucion ? StatusTopico.SOLUCIONADO : StatusTopico.NO_SOLUCIONADO);
        }
    }

    @Transactional
    public void remover(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        Topico topico = respuesta.getTopico();
        boolean eraSolucion = respuesta.getSolucion();
        respuestaRepository.delete(respuesta);

        long totalRespuestas = respuestaRepository.countByTopicoId(topico.getId());
        if (totalRespuestas == 0) {
            topico.actualizarEstado(StatusTopico.NO_RESPONDIDO);
        } else if (eraSolucion) {
            boolean hayOtraSolucion = respuestaRepository.existsByTopicoIdAndSolucionTrue(topico.getId());
            if (hayOtraSolucion) {
                topico.actualizarEstado(StatusTopico.SOLUCIONADO);
            } else {
                topico.actualizarEstado(StatusTopico.NO_SOLUCIONADO);
            }
        }
    }



}
