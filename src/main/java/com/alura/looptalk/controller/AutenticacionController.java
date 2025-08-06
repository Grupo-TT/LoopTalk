package com.alura.looptalk.controller;

import com.alura.looptalk.domain.usuario.Usuario;
import com.alura.looptalk.domain.usuario.dto.DatosAutenticacionUsuario;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;
import com.alura.looptalk.domain.usuario.dto.RegistroUsuario;
import com.alura.looptalk.domain.usuario.service.UsuarioService;
import com.alura.looptalk.infra.security.DatosJWTToken;
import com.alura.looptalk.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasenia());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @PostMapping("/registro")
    public ResponseEntity<DetalleUsuario> registrarUsuario(@RequestBody @Valid RegistroUsuario datos) {
        DetalleUsuario detalleUsuario = usuarioService.registrar(datos);
        return ResponseEntity.ok(detalleUsuario);
    }

}