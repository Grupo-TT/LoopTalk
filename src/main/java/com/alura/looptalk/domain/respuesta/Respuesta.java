package com.alura.looptalk.domain.respuesta;

import com.alura.looptalk.domain.respuesta.dto.ActualizarRespuesta;
import com.alura.looptalk.domain.respuesta.dto.RegistroRespuesta;
import com.alura.looptalk.domain.topico.Topico;
import com.alura.looptalk.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas", indexes = {
    @Index(name = "idx_respuesta_topico", columnList = "topico_id"),
    @Index(name = "idx_respuesta_autor", columnList = "autor_id"),
    @Index(name = "idx_respuesta_fecha", columnList = "fecha_creacion")
})
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "solucion", nullable = false)
    private Boolean solucion = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id", nullable = false, foreignKey = @ForeignKey(name = "fk_respuesta_topico"))
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_respuesta_usuario"))
    private Usuario autor;

    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.solucion = false;
    }

    public Respuesta(RegistroRespuesta datos, Topico topico, Usuario autor) {
        this.mensaje = datos.mensaje();
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.solucion = false;
    }

    public void marcarComoSolucion() {
        this.solucion = true;
    }

    public void marcarSolucion() {
        this.solucion = !this.solucion;
    }

    public void actualizarMensaje(String nuevoMensaje) {
        if (nuevoMensaje != null && !nuevoMensaje.trim().isEmpty()) {
            this.mensaje = nuevoMensaje;
        }
    }

    public void actualizarDatos(ActualizarRespuesta datos) {
        if (datos.mensaje() != null && !datos.mensaje().trim().isEmpty()) {
            this.mensaje = datos.mensaje();
        }
        if (datos.solucion() != null) {
            this.solucion = datos.solucion();
        }
    }
}
