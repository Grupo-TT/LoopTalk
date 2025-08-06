package com.alura.looptalk.domain.usuario;

import com.alura.looptalk.domain.usuario.dto.ActualizarUsuario;
import com.alura.looptalk.domain.usuario.dto.RegistroUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.name()));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
