package com.example.demo.services;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.AsistenciaDTO;
import com.example.demo.entities.Asistencia;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.AsistenciaRepository;
import com.example.demo.utils.StringGenerator;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;

@Service
@Slf4j
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    private ExpiringMap<Long, String> tokens = ExpiringMap.builder()
            .expiration(1, TimeUnit.MINUTES) // 1 minuto
            .maxSize(1000) // Tamaño máximo del mapa
            .asyncExpirationListener((key, value) -> {
                generarToken((Long) key);
            })
            .build();

    public void toggleSesion(Long idSesion) {
        if (!tokens.containsKey(idSesion)) {
            generarToken(idSesion);
        } else {
            tokens.remove(idSesion);
            log.info("Token de asistencia para la sesión {} ha sido desactivado.", idSesion);
        }
    }

    public List<AsistenciaDTO> obtenerAsistenciasPorSesion(Long idSesion) {
        return asistenciaRepository.findBySesion_Id(idSesion).stream()
                .map(asistencia -> {
                    AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
                    asistenciaDTO.parseFromEntity(asistencia);
                    return asistenciaDTO;
                })
                .collect(Collectors.toList());
    }

    private void generarToken(Long id) {
        String tokenGenerado = StringGenerator.generateRandomString();
        tokens.put(id, tokenGenerado);
        log.info("Token generado para ID {}: {}", id, tokenGenerado);
    }

    public String getToken(Long idSesion) {
        String token = tokens.get(idSesion);
        if (token == null) {
            throw new ResourceNotFoundException(
                    "No se encontró un token de asistencia activo para la sesión " + idSesion);
        }
        return token;
    }

    @Transactional
    public void toggleAsistencia(Long idSesion, Long idParticipante) {
        Asistencia asistencia = asistenciaRepository.findBySesion_IdAndParticipante_Id(idSesion, idParticipante)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asistencia no encontrada para la sesión y participante especificados."));

        asistencia.setAsistio(!asistencia.isAsistio());
        log.info("Asistencia para el participante {} en la sesión {} ha sido cambiada a {}",
                idParticipante, idSesion, asistencia.isAsistio());

        asistenciaRepository.save(asistencia);
    }

    @Transactional
    public void marcarAsistencia(Long idSesion, Long idParticipante, String token) {
        Asistencia asistencia = asistenciaRepository.findBySesion_IdAndParticipante_Id(idSesion, idParticipante)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asistencia no encontrada para la sesión y participante especificados."));

        if (asistencia.isAsistio()) {
            return;
        }

        String tokenGenerado = this.tokens.get(idSesion);

        if(tokenGenerado == null) {
            throw new ResourceNotFoundException("No se encontró un token de asistencia activo para la sesión " + idSesion);
        }

        if (!tokenGenerado.equals(token)) {
            throw new IllegalArgumentException("Token de asistencia inválido.");
        }

        asistencia.setAsistio(true);
        asistenciaRepository.save(asistencia);
        log.info("Asistencia marcada para el participante {} en la sesión {}", idParticipante, idSesion);
    }
}
