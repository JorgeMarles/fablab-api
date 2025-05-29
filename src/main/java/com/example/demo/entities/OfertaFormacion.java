package com.example.demo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
    @Digits(integer = 4, fraction = 0, message = "Codigo CINE debe ser un codigo numerico de máximo 4 dígitos")
    private Integer cine;

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
    @ManyToMany
    @JoinTable(name = "oferta_tipobeneficiario",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_beneficiario_id"))
    private List<TipoBeneficiario> tiposBeneficiario;

    private Integer valor;

    @NotNull(message = "CUPOMÁXIMO no puede ser nulo")
    @Digits(integer = 10, fraction = 0, message = "CUPOMÁXIMO debe ser un número entero de máximo 10 dígitos")
    private Integer cupoMaximo;

    @OneToOne
    @JoinColumn(name = "pieza_grafica_id", referencedColumnName = "uuid")
    private Archivo piezaGrafica;

    private Integer semestre;

    //REVISAR
    @ManyToMany
    @JoinTable(name = "oferta_institucion",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "institucion_id"))
    private List<Institucion> instituciones;

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Sesion> sesiones = new ArrayList<>();

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @OneToMany(mappedBy = "ofertaFormacion")
    private List<Certificado> certificados = new ArrayList<>();

    public List<Instructor> getInstructores() {
        Set<Instructor> instructores = new TreeSet<>(
            (i1, i2) -> {
                if (i1.getId() == null || i2.getId() == null) {
                    return 0; // No comparar si alguno es nulo
                }
                return i1.getId().compareTo(i2.getId());
            }
        );
        for (Sesion sesion : sesiones) {
            instructores.addAll(sesion.getInstructores());
        }
        return instructores.stream().toList();
    }

    public void addInstitucion(Institucion institucion) {
        instituciones.add(institucion);
        institucion.getOfertasFormacion().add(this);
    }

    public void removeInstitucion(Institucion institucion) {
        instituciones.remove(institucion);
        institucion.getOfertasFormacion().remove(this);
    }

    public void addTipoBeneficiario(TipoBeneficiario tipoBeneficiario) {
        tiposBeneficiario.add(tipoBeneficiario);
        tipoBeneficiario.getOfertasFormacion().add(this);
    }

    public void removeTipoBeneficiario(TipoBeneficiario tipoBeneficiario) {
        tiposBeneficiario.remove(tipoBeneficiario);
        tipoBeneficiario.getOfertasFormacion().remove(this);
    }

    public void clearTiposBeneficiario() {
        for (TipoBeneficiario tipoBeneficiario : tiposBeneficiario) {
            tipoBeneficiario.getOfertasFormacion().remove(this);
        }
        tiposBeneficiario.clear();
    }

    public void clearInstituciones() {
        for (Institucion institucion : instituciones) {
            institucion.getOfertasFormacion().remove(this);
        }
        instituciones.clear();
    }
}