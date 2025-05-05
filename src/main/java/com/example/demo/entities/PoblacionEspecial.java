package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "poblacion_especial")
public class PoblacionEspecial extends BaseEntity {
    public PoblacionEspecial(Long id, String nombre) {
        super(id, nombre);
    }
}
