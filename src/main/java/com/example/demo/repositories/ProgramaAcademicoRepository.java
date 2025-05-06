
package com.example.demo.repositories;

import com.example.demo.entities.ProgramaAcademico;
import java.util.Optional;

public interface ProgramaAcademicoRepository extends GenericRepository<ProgramaAcademico> {
    Optional<ProgramaAcademico> findById(Long id);
    Optional<ProgramaAcademico> findByNombre(String nombre);
    Optional<ProgramaAcademico> findByCodigo(String codigo);
}
