package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
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
@Table(name = "institucion")
public class Institucion extends BaseEntity {
    public Institucion(Long id, String nombre, TipoInstitucion tipoInstitucion) {
        super(id, nombre);
        this.tipoInstitucion = tipoInstitucion;
    }
    
    @Enumerated(EnumType.STRING)
    private TipoInstitucion tipoInstitucion;

    @OneToMany(mappedBy = "institucion")
    private List<RegistroIngreso> registroIngresos = new ArrayList<>();;

    @ManyToMany(mappedBy = "instituciones")
    private List<OfertaFormacion> ofertasFormacion = new ArrayList<>();;
}

