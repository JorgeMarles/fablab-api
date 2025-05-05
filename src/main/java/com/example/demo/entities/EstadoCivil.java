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
@Table(name = "estado_civil")
public class EstadoCivil extends BaseEntity {
    public EstadoCivil(Long id, String nombre) {
        super(id, nombre);
    }
}