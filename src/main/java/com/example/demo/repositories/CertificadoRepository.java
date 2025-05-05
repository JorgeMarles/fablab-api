package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Certificado;

public interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    List<Certificado> findByInstructor_Id(Long instructorId);
    List<Certificado> findByOfertaFormacion_Id(Long ofertaFormacionId);
    List<Certificado> findByPlantilla_Id(Long plantillaId);
}
