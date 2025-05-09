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
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "NOMBRE no puede ser nulo")
    @Size(max = 200, message = "NOMBRE no puede exceder 200 caracteres")
    private String nombre;

    @NotNull(message = "CÓDIGO no puede ser nulo")
    @Size(max = 20, message = "CÓDIGO no puede exceder 20 caracteres")
    private String codigo;

    @NotNull(message = "CINE no puede ser nulo")
    @Pattern(regexp = "\\d{1,4}", message = "CINE debe contener solo números y máximo 4 dígitos")
    private String cine;

    @NotNull(message = "EXTENSIÓN no puede ser nulo")
    private boolean extension;
    
    @Enumerated(EnumType.STRING)
    private EstadoOfertaFormacion estado;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    @NotNull(message = "HORAS no puede ser nulo")
    @Digits(integer = 5, fraction = 0, message = "HORAS debe ser un número entero de máximo 5 dígitos")
    private Integer horas;
    
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

    private Integer valor;

    @NotNull(message = "CUPOMÁXIMO no puede ser nulo")
    @Digits(integer = 10, fraction = 0, message = "CUPOMÁXIMO debe ser un número entero de máximo 10 dígitos")
    private Integer cupoMaximo;

    private String piezaGrafica;

    private Integer semestre;

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