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
@Table(name = "pais")
public class Pais extends BaseEntity {
    private String codigo;
    public Pais(Long id, String nombre, String codigo) {
        super(id, nombre);
        this.codigo = codigo;
    }
}
