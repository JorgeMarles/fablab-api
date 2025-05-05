package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findBySesion_Id(Long sesionId);
    List<Asistencia> findByParticipante_Id(Long participanteId);
    Optional<Asistencia> findBySesion_IdAndParticipante_Id(Long sesionId, Long participanteId);
}