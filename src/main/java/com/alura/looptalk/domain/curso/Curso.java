package com.alura.looptalk.domain.curso;

import com.alura.looptalk.domain.curso.dto.ActualizarCurso;
import com.alura.looptalk.domain.curso.dto.RegistroCurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;

    public Curso(RegistroCurso curso) {
        this.nombre = curso.nombre();
        this.categoria = curso.categoria();
    }

    public void actualizarDatos(ActualizarCurso curso) {
        if (curso.nombre() != null && !curso.nombre().trim().isEmpty()) {
            this.nombre = curso.nombre();
        }
        if (curso.categoria() != null && !curso.categoria().trim().isEmpty()) {
            this.categoria = curso.categoria();
        }
    }

}
