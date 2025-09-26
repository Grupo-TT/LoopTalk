package com.alura.looptalk.domain.curso;

import com.alura.looptalk.domain.curso.dto.ActualizarCurso;
import com.alura.looptalk.domain.curso.dto.RegistroCurso;
import com.alura.looptalk.domain.topico.Topico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Topico> topicos = new ArrayList<>();

    public Curso(RegistroCurso curso) {
        this.nombre = curso.nombre();
        this.categoria = curso.categoria();
        this.topicos = new ArrayList<>();
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