package com.alura.looptalk.domain.usuario.dto;

import com.alura.looptalk.domain.usuario.Roles;

public record ActualizarUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        String contrasenia,
        Roles rol
) {
}
