package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Evidencia;

import java.util.List;

public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {
	List<Evidencia> findBySesion_Id(Long sesionId);
}
