package com.example.demo.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.HistoricoUsuario;

public interface HistoricoUsuarioRepository extends JpaRepository<HistoricoUsuario, Long> {
    @Query("SELECT h FROM HistoricoUsuario h " +
           "WHERE h.usuario.id = :usuarioId " +
           "AND h.campo = :campo " +
           "AND h.fechaModificacion <= :fecha " +
           "ORDER BY h.fechaModificacion DESC " +
           "LIMIT 1")
    Optional<HistoricoUsuario> findLatestHistoryBeforeDate(
        @Param("usuarioId") Long usuarioId,
        @Param("campo") String campo,
        @Param("fecha") LocalDateTime fecha
    );
}
