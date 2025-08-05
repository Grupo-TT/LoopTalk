package com.alura.looptalk.domain.usuario;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.topico.StatusTopico;
import com.alura.looptalk.domain.usuario.dto.ActualizarUsuario;
import com.alura.looptalk.domain.usuario.dto.DetalleUsuario;
import com.alura.looptalk.domain.usuario.dto.RegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correoElectronico;

    private String contrasenia;

    @Column(name = "rol_usuario")
    @Enumerated(EnumType.STRING)
    private Roles rol;

    public Usuario(RegistroUsuario usuario) {
        this.nombre = usuario.nombre();
        this.correoElectronico = usuario.correoElectronico();
        this.contrasenia = usuario.contrasenia();
        this.rol = usuario.rol();
    }

    public void actualizarDatos(ActualizarUsuario usuario) {
        if (usuario.nombre() != null && !usuario.nombre().trim().isEmpty()) {
            this.nombre = usuario.nombre();
        }

        if (usuario.correoElectronico() != null && !usuario.correoElectronico().trim().isEmpty()) {
            this.correoElectronico = usuario.correoElectronico();
        }

        if (usuario.contrasenia() != null) {
            this.contrasenia = usuario.contrasenia();
        }

        if (usuario.rol() != null) {
            this.rol = usuario.rol();
        }
    }
}
