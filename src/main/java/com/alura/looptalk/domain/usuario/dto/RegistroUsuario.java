package com.alura.looptalk.domain.usuario.dto;

import com.alura.looptalk.domain.usuario.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroUsuario(
        @NotBlank String nombre,
        @NotBlank @Email String correoElectronico,
        @NotBlank String contrasenia,
        @NotNull Roles rol
) {
}
