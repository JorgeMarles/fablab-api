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
@Table(name = "cargo")
public class Cargo extends BaseEntity {

    public Cargo(Long id, String nombre) {
        super(id, nombre);
    }

    @OneToMany(mappedBy = "cargo")
    private List<RegistroIngreso> registroIngresos = new ArrayList<>();
}
