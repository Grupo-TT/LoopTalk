package com.alura.looptalk.controller;

import com.alura.looptalk.domain.respuesta.dto.DetalleRespuesta;
import com.alura.looptalk.domain.respuesta.dto.RegistroRespuesta;
import com.alura.looptalk.domain.respuesta.repository.RespuestaRepository;
import com.alura.looptalk.domain.respuesta.service.RespuestaService;
import com.alura.looptalk.domain.topico.dto.ActualizarTopico;
import com.alura.looptalk.domain.topico.dto.RegistroTopico;
import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.topico.service.TopicoService;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaService respuestaService;


    @GetMapping
    public ResponseEntity<Page<DetalleTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DetalleTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleTopico> obtenerTopico(@PathVariable Long id){
        return ResponseEntity.ok(new DetalleTopico(topicoRepository.getReferenceById(id)));
    }

    @PostMapping
    public ResponseEntity<DetalleTopico> registrarTopico(@RequestBody @Valid RegistroTopico datos) {
        DetalleTopico detalleTopico = topicoService.registrar(datos);
        return ResponseEntity.ok(detalleTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopico datos){
        var detalle = topicoService.actualizar(id, datos);
        return ResponseEntity.ok(detalle);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        topicoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/respuestas")
    public ResponseEntity<Page<DetalleRespuesta>> obtenerRespuestas(@PathVariable Long id, @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }
        var respuestas = respuestaRepository.findByTopicoId(id, paginacion).map(DetalleRespuesta::new);

        if (respuestas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(respuestas);
    }

    @PostMapping("/{id}/respuestas")
    @Transactional
    public ResponseEntity<DetalleRespuesta> registrarRespuestaEnTopico(@PathVariable Long id, @RequestBody @Valid RegistroRespuesta datos) {
        var respuesta = respuestaService.registrar(datos, id);
        return ResponseEntity.ok(respuesta);
    }


}
