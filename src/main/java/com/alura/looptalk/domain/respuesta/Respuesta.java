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

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private boolean solucion;

    public boolean getSolucion() {
        return this.solucion;
    }

    public Respuesta(RegistroRespuesta respuesta, Topico topico, Usuario autor) {
        this.mensaje = respuesta.mensaje();
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.solucion = false;
    }

    public void marcarSolucion() {
        this.solucion = !this.solucion;
    }

    public void actualizarDatos(ActualizarRespuesta respuesta) {
        if (respuesta.mensaje() != null && !respuesta.mensaje().trim().isEmpty()) {
            this.mensaje = respuesta.mensaje();
        }

        if (respuesta.solucion() != null) {
            this.solucion = respuesta.solucion();
        }
    }

}
