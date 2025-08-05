package com.alura.looptalk.domain.topico;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = StatusTopico.NO_RESPONDIDO;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(String titulo, String mensaje, Usuario autor, StatusTopico status, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    public void actualizarDatos(String titulo, String mensaje, StatusTopico status, Curso curso) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }

        if (mensaje != null && !mensaje.trim().isEmpty()) {
            this.mensaje = mensaje;
        }

        if (status != null) {
            this.status = status;
        }

        if (curso != null) {
            this.curso = curso;
        }
    }


}
