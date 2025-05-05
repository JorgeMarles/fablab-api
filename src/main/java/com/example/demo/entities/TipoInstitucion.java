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
@Table(name = "tipo_institucion")
public class TipoInstitucion extends BaseEntity {
    public TipoInstitucion(Long id, String nombre) {
        super(id, nombre);
    }
    @OneToMany(mappedBy = "tipoInstitucion")
    private List<Institucion> instituciones;
}