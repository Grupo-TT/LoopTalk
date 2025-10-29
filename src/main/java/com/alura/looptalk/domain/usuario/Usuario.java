package com.alura.looptalk.domain.usuario;

import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.usuario.dto.ActualizarUsuario;
import com.alura.looptalk.domain.usuario.dto.RegistroUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios", indexes = {
    @Index(name = "idx_usuario_correo", columnList = "correo_electronico", unique = true)
})
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

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "rol_usuario", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Topico> topicos = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Respuesta> respuestas = new ArrayList<>();

    public Usuario(RegistroUsuario usuario) {
        this.nombre = usuario.nombre();
        this.correoElectronico = usuario.correoElectronico();
        this.contrasenia = usuario.contrasenia();
        this.rol = usuario.rol();
        this.topicos = new ArrayList<>();
        this.respuestas = new ArrayList<>();
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



    public void actualizarDatos(ActualizarUsuario usuario, PasswordEncoder passwordEncoder) {
        if (usuario.nombre() != null && !usuario.nombre().trim().isEmpty()) {
            this.nombre = usuario.nombre();
        }

        if (usuario.correoElectronico() != null && !usuario.correoElectronico().trim().isEmpty()) {
            this.correoElectronico = usuario.correoElectronico();
        }

        if (usuario.contrasenia() != null) {
            this.contrasenia = passwordEncoder.encode(usuario.contrasenia());
        }

        if (usuario.rol() != null) {
            this.rol = usuario.rol();
        }
    }
}
