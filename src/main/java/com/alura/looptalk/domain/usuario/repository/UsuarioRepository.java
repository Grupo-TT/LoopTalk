package com.alura.looptalk.domain.usuario.repository;

import com.alura.looptalk.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreoElectronico(String correoElectronico);

    UserDetails findByCorreoElectronico(String correoElectronico);
}