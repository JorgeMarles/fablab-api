package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime inicio;
    private LocalTime fin;
    @ManyToOne
    @JoinColumn(name = "oferta_formacion_id")
    private OfertaFormacion ofertaFormacion;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;    

    @OneToMany(mappedBy = "sesion")
    private List<Evidencia> evidencias;

    @ManyToMany(mappedBy = "sesiones")
    private List<Instructor> instructores;

    @OneToMany(mappedBy = "sesion")
    private List<Asistencia> asistencias;
}