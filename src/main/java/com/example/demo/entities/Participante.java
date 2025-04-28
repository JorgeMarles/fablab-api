package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_estado_civil")
    private EstadoCivil estadoCivil;

    private String correoInstitucional;

    @ManyToOne
    @JoinColumn(name = "id_poblacion_especial")
    private PoblacionEspecial poblacionEspecial;

    @OneToMany(mappedBy = "participante")
    private List<Asistencia> asistencias;

    @ManyToMany(mappedBy = "participantes")
    private List<OfertaFormacion> ofertaFormaciones;
}
