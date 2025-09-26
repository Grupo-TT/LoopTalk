package com.alura.looptalk.domain.topico;

import com.alura.looptalk.domain.curso.Curso;
import com.alura.looptalk.domain.respuesta.Respuesta;
import com.alura.looptalk.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos", indexes = {
    @Index(name = "idx_topico_autor", columnList = "autor_id"),
    @Index(name = "idx_topico_curso", columnList = "curso_id"),
    @Index(name = "idx_topico_fecha", columnList = "fecha_creacion")
})
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_topico_usuario"))
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false, foreignKey = @ForeignKey(name = "fk_topico_curso"))
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = StatusTopico.NO_RESPONDIDO;
        this.autor = autor;
        this.curso = curso;
        this.respuestas = new ArrayList<>();
    }

    public Topico(String titulo, String mensaje, Usuario autor, StatusTopico status, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.status = status;
        this.autor = autor;
        this.curso = curso;
        this.respuestas = new ArrayList<>();
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

    public void actualizarEstado(StatusTopico nuevoEstado) {
        this.status = nuevoEstado;
    }


}
