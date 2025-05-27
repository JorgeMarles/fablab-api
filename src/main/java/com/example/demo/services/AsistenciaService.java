package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.AsistenciaDTO;
import com.example.demo.DTO.response.AsistenciaTokenDTO;
import com.example.demo.entities.Asistencia;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.AsistenciaRepository;
import com.example.demo.utils.StringGenerator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;

@Service
@Slf4j
public class AsistenciaService {

    private static final long EXPIRATION_MINUTES = 1; // Tiempo de expiración en minutos

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Data
    @AllArgsConstructor
    private static class TokenValue {
        private String token;
        private LocalDateTime expiracion;
    }

    private ExpiringMap<Long, TokenValue> tokens = ExpiringMap.builder()
            .expiration(EXPIRATION_MINUTES, TimeUnit.MINUTES) // Tiempo de expiración del token
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
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        tokens.put(id, new TokenValue(tokenGenerado, expiracion));
        log.info("Token generado para ID {}: {}, vence el {}", id, tokenGenerado, expiracion);
    }

    public AsistenciaTokenDTO getToken(Long idSesion) {
        TokenValue token = tokens.get(idSesion);
        if (token == null) {
            throw new ResourceNotFoundException(
                    "No se encontró un token de asistencia activo para la sesión " + idSesion);
        }
        AsistenciaTokenDTO dto = new AsistenciaTokenDTO(token.getToken(), token.getExpiracion());
        return dto;
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

        TokenValue tokenGenerado = this.tokens.get(idSesion);

        if (tokenGenerado == null) {
            throw new ResourceNotFoundException(
                    "No se encontró un token de asistencia activo para la sesión " + idSesion);
        }

        if (!tokenGenerado.getToken().equals(token)) {
            throw new IllegalArgumentException("Token de asistencia inválido.");
        }

        asistencia.setAsistio(true);
        asistenciaRepository.save(asistencia);
        log.info("Asistencia marcada para el participante {} en la sesión {}", idParticipante, idSesion);
    }
}
