package com.alura.looptalk.controller;

import com.alura.looptalk.domain.usuario.Usuario;
import com.alura.looptalk.domain.usuario.dto.ActualizarUsuario;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;
import com.alura.looptalk.domain.usuario.repository.UsuarioRepository;
import com.alura.looptalk.domain.usuario.service.UsuarioService;
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
@RequestMapping("usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<DetalleUsuario>> listadoUsuarios(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DetalleUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuario> obtenerUsuario(@PathVariable Long id){
        return ResponseEntity.ok(new DetalleUsuario(usuarioRepository.getReferenceById(id)));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ActualizarUsuario datos, @AuthenticationPrincipal Usuario autor){
        var detalle = usuarioService.actualizar(id, datos, autor);
        return ResponseEntity.ok(detalle);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id, @AuthenticationPrincipal Usuario autor) {
        usuarioService.remover(id, autor);
        return ResponseEntity.noContent().build();
    }

}
