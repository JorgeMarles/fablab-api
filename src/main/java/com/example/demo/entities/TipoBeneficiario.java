package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "tipoBeneficiario")
    private List<OfertaFormacion> ofertaFormaciones;
}