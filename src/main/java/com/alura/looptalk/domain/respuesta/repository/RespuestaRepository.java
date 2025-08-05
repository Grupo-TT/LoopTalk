package com.alura.looptalk.domain.respuesta.repository;

import com.alura.looptalk.domain.respuesta.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    Page<Respuesta> findByTopicoId(Long topicoId, Pageable pageable);

    boolean existsByTopicoIdAndSolucionTrue(Long topicoId);

    long countByTopicoId(Long topicoId);

}