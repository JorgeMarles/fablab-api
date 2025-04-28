package com.example.demo.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "oferta_formacion")
public class OfertaFormacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String cima;
    private String sesion;
    private boolean activo;
    private String finalidad;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String hora;
    private double valor;
    private double cupoMaximo;
    private String prioridad;
    private String institucion;
    private String semestre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_beneficiario")
    private TipoBeneficiario tipoBeneficiario;

    @ManyToOne
    @JoinColumn(name = "id_categoria_oferta")
    private CategoriaOferta categoriaOferta;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<OfertaFormacionInstructor> ofertaFormacionInstructors;

    @ManyToMany
    @JoinTable(
            name = "inscripcion",
            joinColumns = @JoinColumn(name = "id_oferta_formacion"),
            inverseJoinColumns = @JoinColumn(name = "id_participante")
    )
    private List<Participante> participantes;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Sesion> sesiones;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Inscripcion> inscripciones;
}