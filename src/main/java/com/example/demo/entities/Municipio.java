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
@Table(name = "municipio")
public class Municipio extends BaseEntity {
    @Pattern(regexp = "^[0-9]{5}$", message = "El codigo del municipio debe tener 5 digitos según codigo DIVIPOLA")
    @Column(unique = true, nullable = false)
    private String codigo;
    public Municipio(Long id, String nombre, String codigo) {
        super(id, nombre);
        this.codigo = codigo;
    }
}
