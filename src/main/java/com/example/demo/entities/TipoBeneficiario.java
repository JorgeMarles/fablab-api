package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tipo_beneficiario")
public class TipoBeneficiario extends BaseEntity {
    public TipoBeneficiario(Long id, String nombre) {
        super(id, nombre);
    }

    @ManyToMany(mappedBy = "tiposBeneficiario")
    private List<OfertaFormacion> ofertasFormacion = new ArrayList<>();;
}