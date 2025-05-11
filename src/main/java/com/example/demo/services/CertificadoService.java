package com.example.demo.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Certificado;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.repositories.CertificadoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CertificadoService {
    @Autowired
    private CertificadoRepository certificadoRepository;

    public void crearCertificado(OfertaFormacion oferta, Instructor instructor, LocalDate fecha, PlantillaCertificado plantilla) {
        // Crear un nuevo certificado
        Certificado certificado = new Certificado();
        certificado.setFecha(fecha);
        certificado.setInstructor(instructor);
        certificado.setOfertaFormacion(oferta);
        certificado.setPlantilla(plantilla);

        // Guardar el certificado en la base de datos
        certificadoRepository.save(certificado);
    }
}
