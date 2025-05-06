package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9]{3}$", message = "El codigo del pais debe tener 3 digitos según ISO-3166-1 alpha-3")
    @Column(unique = true, nullable = false)
    private String codigo;
    public Pais(Long id, String nombre, String codigo) {
        super(id, nombre);
        this.codigo = codigo;
    }
}
