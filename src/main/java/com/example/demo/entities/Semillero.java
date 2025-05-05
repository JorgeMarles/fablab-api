package com.example.demo.entities;

import jakarta.persistence.Entity;
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
@Table(name = "semillero")
public class Semillero extends BaseEntity {
    private String siglas;

    public Semillero(Long id, String nombre, String siglas) {
        super(id, nombre);
        this.siglas = siglas;
    }
}
