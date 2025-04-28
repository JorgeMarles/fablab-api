package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registro_ingreso")
public class RegistroIngreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private LocalDateTime hora;

    @ManyToOne
    @JoinColumn(name = "id_motivo")
    private Motivo motivo;

    @ManyToOne
    @JoinColumn(name = "id_institucion")
    private Institucion institucion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_institucion")
    private TipoInstitucion tipoInstitucion;

    private String codigo;
    private String aula;
    private String materia;
    private String idSemillero;
    private String valorSiglaSemillero;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_modalidad")
    private Modalidad modalidad;
}

