package com.alura.looptalk.controller;

import com.alura.looptalk.domain.curso.dto.ActualizarCurso;
import com.alura.looptalk.domain.curso.dto.DetalleCurso;
import com.alura.looptalk.domain.curso.dto.RegistroCurso;
import com.alura.looptalk.domain.curso.repository.CursoRepository;
import com.alura.looptalk.domain.curso.service.CursoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("curso")
//@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<Page<DetalleCurso>> ListadoCursos(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(cursoRepository.findAll(paginacion).map(DetalleCurso::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCurso> obtenerCurso(@PathVariable Long id){
        return ResponseEntity.ok(new DetalleCurso(cursoRepository.getReferenceById(id)));
    }

    @PostMapping
    public ResponseEntity<DetalleCurso> registrarCurso(@RequestBody @Valid RegistroCurso datos) {
        DetalleCurso detalleCurso = cursoService.registrar(datos);
        return ResponseEntity.ok(detalleCurso);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleCurso> actualizarCurso(@PathVariable Long id, @RequestBody @Valid ActualizarCurso datos){
        var detalle = cursoService.actualizar(id, datos);
        return ResponseEntity.ok(detalle);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
        cursoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
