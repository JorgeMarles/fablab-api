package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.OfertaFormacion;

public interface OfertaFormacionRepository extends JpaRepository<OfertaFormacion, Long> {
    Optional<OfertaFormacion> findByCodigo(String codigo);
    Optional<OfertaFormacion> findByCine(String cine);
    List<OfertaFormacion> findByExtension(boolean extension);
    List<OfertaFormacion> findByCategoria_Id(Long categoriaId);
    List<OfertaFormacion> findByTipo_Id(Long tipoId);
}
