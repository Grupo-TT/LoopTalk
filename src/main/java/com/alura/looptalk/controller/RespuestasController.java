package com.alura.looptalk.controller;

import com.alura.looptalk.domain.respuesta.dto.ActualizarRespuesta;
import com.alura.looptalk.domain.respuesta.dto.DetalleRespuesta;
import com.alura.looptalk.domain.respuesta.repository.RespuestaRepository;
import com.alura.looptalk.domain.respuesta.service.RespuestaService;
import com.alura.looptalk.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestasController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaService respuestaService;


    @GetMapping
    public ResponseEntity<Page<DetalleRespuesta>> listadoRespuestas(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DetalleRespuesta::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRespuesta> obtenerRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(new DetalleRespuesta(respuestaRepository.getReferenceById(id)));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleRespuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid ActualizarRespuesta datos, @AuthenticationPrincipal Usuario autor){
        var detalle = respuestaService.actualizar(id, datos, autor);
        return ResponseEntity.ok(detalle);
    }

    @PutMapping("/{id}/solucionar")
    @Transactional
    public ResponseEntity marcarComoSolucion(@PathVariable Long id, @AuthenticationPrincipal Usuario autor) {
        respuestaService.marcarSolucion(id, autor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id, @AuthenticationPrincipal Usuario autor) {
        respuestaService.remover(id, autor);
        return ResponseEntity.noContent().build();
    }

}