package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programa_academico")
public class ProgramaAcademico extends BaseEntity {
    
    public ProgramaAcademico(Long id, String nombre, String codigo) {
        super(id, nombre);
        this.codigo = codigo;
    }

    private String codigo;

    @OneToMany(mappedBy = "programaAcademico")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "programaAcademico")
    private List<RegistroIngreso> registrosIngreso;
}