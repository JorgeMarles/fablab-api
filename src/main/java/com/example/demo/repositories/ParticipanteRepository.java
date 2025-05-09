package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByUsuario_Documento(String documento);
    
    @Query("""
        SELECT p FROM Participante p 
        WHERE CONCAT(p.usuario.primerNombre, ' ', p.usuario.segundoNombre, ' ', p.usuario.primerApellido, ' ', p.usuario.segundoApellido) LIKE %:search% 
        OR p.usuario.documento LIKE %:search% or p.usuario.correoPersonal LIKE %:search%
        """)
    List<Participante> findBySearch(String search);
}