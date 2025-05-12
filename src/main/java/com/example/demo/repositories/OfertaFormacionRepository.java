package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.OfertaFormacion;

public interface OfertaFormacionRepository extends JpaRepository<OfertaFormacion, Long> {
    Optional<OfertaFormacion> findByCodigo(String codigo);

    Optional<OfertaFormacion> findByCine(Integer cine);

    List<OfertaFormacion> findByExtension(boolean extension);

    List<OfertaFormacion> findByCategoria_Id(Long categoriaId);

    List<OfertaFormacion> findByTipo_Id(Long tipoId);

    @Query("SELECT DISTINCT o FROM OfertaFormacion o " +
            "JOIN o.sesiones s " +
            "JOIN s.instructores i " +
            "WHERE i.id = :instructorId")
    List<OfertaFormacion> findByInstructorId(@Param("instructorId") Long instructorId);
}
