package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByParticipante_Id(Long participanteId);
    List<Inscripcion> findByOfertaFormacion_Id(Long ofertaFormacionId);
}
