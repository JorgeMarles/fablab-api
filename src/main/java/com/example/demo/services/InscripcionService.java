package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Asistencia;
import com.example.demo.entities.Inscripcion;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.Participante;
import com.example.demo.entities.Sesion;
import com.example.demo.repositories.AsistenciaRepository;
import com.example.demo.repositories.InscripcionRepository;

import jakarta.transaction.Transactional;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    List<Inscripcion> inscripcionesPorParticipante(Long participanteId) {
        return inscripcionRepository.findByParticipante_Id(participanteId);
    }

    List<Inscripcion> inscripcionesPorOfertaFormacion(Long ofertaId) {
        return inscripcionRepository.findByOfertaFormacion_Id(ofertaId);
    }

    @Transactional
    public Inscripcion crear(OfertaFormacion oferta, Participante participante) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setOfertaFormacion(oferta);
        oferta.getInscripciones().add(inscripcion);
        inscripcion.setParticipante(participante);
        participante.getInscripciones().add(inscripcion);
        inscripcion.setProgramaAcademico(null);

        for(Sesion sesion : oferta.getSesiones()) {
            Asistencia asistencia = new Asistencia();
            asistencia.setSesion(sesion);
            asistencia.setParticipante(participante);
            asistencia.setAsistio(false);
            sesion.getAsistencias().add(asistencia);
            participante.getAsistencias().add(asistencia);
            asistenciaRepository.save(asistencia);
        }

        return inscripcionRepository.save(inscripcion);
    }

    @Transactional
    public void eliminar(Long idParticipante, Long idOfertaFormacion) {
        Inscripcion inscripcion = inscripcionRepository.findByParticipante_IdAndOfertaFormacion_Id(idParticipante, idOfertaFormacion)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada."));
        
        // Eliminar las asistencias asociadas a la inscripción
        for (Asistencia asistencia : inscripcion.getOfertaFormacion().getSesiones().stream()
                .flatMap(sesion -> sesion.getAsistencias().stream()).toList()) {
            if (asistencia.getParticipante().getId().equals(idParticipante)) {
                asistenciaRepository.delete(asistencia);
            }
        }

        // Eliminar la inscripción
        inscripcionRepository.delete(inscripcion);
    }
}
