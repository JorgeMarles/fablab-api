package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sala")
public class Sala extends BaseEntity {
    public Sala(){
    }
    public Sala(Long id, String nombre) {
        super(id, nombre);
    }
}