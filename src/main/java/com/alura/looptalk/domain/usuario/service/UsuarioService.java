package com.alura.looptalk.domain.usuario.service;

import com.alura.looptalk.domain.usuario.Usuario;
import com.alura.looptalk.domain.usuario.dto.ActualizarUsuario;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;
import com.alura.looptalk.domain.usuario.dto.RegistroUsuario;
import com.alura.looptalk.domain.usuario.repository.UsuarioRepository;
import com.alura.looptalk.infra.exceptions.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DetalleUsuario registrar(RegistroUsuario datos) {
        if (usuarioRepository.existsByCorreoElectronico(datos.correoElectronico())) {
            throw new ValidacionException("Ya existe un usuario registrado con ese correo electrónico.");
        }
        Usuario usuario = new Usuario(datos);
        usuario.setContrasenia(passwordEncoder.encode(datos.contrasenia()));
        usuarioRepository.save(usuario);

        return new DetalleUsuario(usuario);
    }

    public DetalleUsuario actualizar(Long id, ActualizarUsuario datos) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        usuario.actualizarDatos(datos);
        return new DetalleUsuario(usuario);
    }

    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}
