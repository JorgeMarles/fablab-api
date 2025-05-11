package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Institucion;
import com.example.demo.entities.TipoInstitucion;

public interface InstitucionRepository extends GenericRepository<Institucion> {
    List<Institucion> findByTipoInstitucion(TipoInstitucion tipoInstitucion);
}
