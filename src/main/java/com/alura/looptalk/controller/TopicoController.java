package com.alura.looptalk.controller;

import com.alura.looptalk.domain.topico.dto.ActualizarTopico;
import com.alura.looptalk.domain.topico.dto.RegistroTopico;
import com.alura.looptalk.domain.topico.dto.DetalleTopico;
import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.topico.service.TopicoService;
import com.alura.looptalk.domain.usuario.Usuario;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.domain.topico.repository.TopicoRepository;
import com.alura.looptalk.domain.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("topico")
//@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;




    @GetMapping
    public ResponseEntity<Page<DetalleTopico>> listadoMedicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
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

}
