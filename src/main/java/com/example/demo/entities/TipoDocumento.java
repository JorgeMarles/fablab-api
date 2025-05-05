package com.example.demo.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento extends BaseEntity {
    private String siglas;

    public TipoDocumento(Long id, String nombre, String siglas) {
        super(id, nombre);
        this.siglas = siglas;
    }
}