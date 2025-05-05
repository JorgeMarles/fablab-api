package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Institucion;

public interface InstitucionRepository extends GenericRepository<Institucion> {
    List<Institucion> findByTipoInstitucion_Id(Long idTipoInstitucion);
}
