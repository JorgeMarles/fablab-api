package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByUsuario_Cedula(String cedula);
    
    @Query("""
        SELECT p FROM Participante p 
        WHERE CONCAT(p.primer_nombre, ' ', p.segundo_nombre, ' ', p.primer_apellido, ' ', p.segundo_apellido) LIKE %:search% 
        OR p.usuario.cedula LIKE %:search% or p.usuario.correo_personal LIKE %:search%
        """)
    List<Participante> findBySearch(String search);
}