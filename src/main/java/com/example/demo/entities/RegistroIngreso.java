package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private LocalDateTime tiempo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    @ManyToOne(optional = true)
    private OfertaFormacion ofertaFormacion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    @Column(nullable = true)
    private String institucionNombre;

    @ManyToOne(optional = true)
    @JoinColumn(name = "programa_academico_id")
    private ProgramaAcademico programaAcademico;

    @Column(nullable = true)
    private String codigoCarrera;

    @Column(nullable = true)
    private String codigo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @Column(nullable = true)
    private String materia;

    @ManyToOne(optional = true)
    @JoinColumn(name = "semillero_id")
    private Semillero semillero;

    @Column(nullable = true)
    private String semilleroNombre;

    @Column(nullable = true)
    private String semilleroSiglas;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private String asociacion;
}
