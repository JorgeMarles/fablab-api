package com.example.demo.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private String cine;
    private boolean extension;
    
    @Enumerated(EnumType.STRING)
    private EstadoOfertaFormacion estado;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaOferta categoria;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoOferta tipo;

    //REVISAR
    @ManyToOne
    @JoinColumn(name = "tipo_beneficiario_id")
    private TipoBeneficiario tipoBeneficiario;

    private int valor;

    private int cupoMaximo;

    private String piezaGrafica;

    private int semestre;

    //REVISAR
    @ManyToOne
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    /*
    @ManyToMany
    @JoinTable(
            name = "inscripcion",
            joinColumns = @JoinColumn(name = "oferta_formacion_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes;
    */

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Sesion> sesiones;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Certificado> certificados;
}