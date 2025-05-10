package com.example.demo.entities;

import java.util.ArrayList;
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
@Table(name = "categoria_oferta")
public class CategoriaOferta extends BaseEntity {
    public CategoriaOferta(Long id, String nombre) {
        super(id, nombre);
    }

    @OneToMany(mappedBy = "categoria")
    private List<OfertaFormacion> ofertas = new ArrayList<>();
}