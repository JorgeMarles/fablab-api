package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
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

    @ManyToOne
    @JoinColumn(name = "institucion_id")
    @Nullable
    private Institucion institucion;

    @Nullable
    private String institucionOtro;

    @ManyToOne
    @JoinColumn(name = "programa_academico_id")
    @Nullable
    private ProgramaAcademico programaAcademico;

    @Nullable
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    @Nullable
    private Sala sala;

    @Nullable
    private String materia;

    @ManyToOne
    @JoinColumn(name = "semillero_id")
    @Nullable
    private Semillero semillero;

    @Nullable
    private String semilleroOtro;

    @Nullable
    private String siglasSemilleroOtro;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
}

