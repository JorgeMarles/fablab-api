package com.example.demo.entities;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participante")
public class Participante {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id")
    private EstadoCivil estadoCivil;

    private String correoInstitucional;

    @Nullable
    private String direccionInstitucional;

    @ManyToOne
    @JoinColumn(name = "poblacion_especial_id")
    private PoblacionEspecial poblacionEspecial;

    @OneToMany(mappedBy = "participante")
    private List<Asistencia> asistencias;

    @OneToMany(mappedBy = "participante")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "participante")
    private List<RegistroIngreso> registroIngresos;
}
