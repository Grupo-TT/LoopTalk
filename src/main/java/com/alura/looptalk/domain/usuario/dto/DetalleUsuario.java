package com.alura.looptalk.domain.usuario.dto;

import com.alura.looptalk.domain.usuario.Roles;
import com.alura.looptalk.domain.usuario.Usuario;

public record DetalleUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        Roles rol) {

    public DetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getRol()
        );
    }
}
