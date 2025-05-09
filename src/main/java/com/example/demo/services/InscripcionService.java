package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Inscripcion;
import com.example.demo.repositories.InscripcionRepository;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    List<Inscripcion> inscripcionesPorParticipante(Long participanteId) {
        return inscripcionRepository.findByParticipante_Id(participanteId);
    }

    List<Inscripcion> inscripcionesPorOfertaFormacion(Long ofertaId) {
        return inscripcionRepository.findByOfertaFormacion_Id(ofertaId);
    }
}
