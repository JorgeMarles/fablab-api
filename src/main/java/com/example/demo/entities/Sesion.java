package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = { "instructores", "ofertaFormacion" })
@EqualsAndHashCode(exclude = { "instructores", "ofertaFormacion" })
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

    @OneToMany(mappedBy = "sesion", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Evidencia> evidencias = new ArrayList<>();

    @ManyToMany(mappedBy = "sesiones")
    private List<Instructor> instructores = new ArrayList<>();

    @OneToMany(mappedBy = "sesion", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Asistencia> asistencias = new ArrayList<>();

    public void addInstructor(Instructor instructor) {
        instructores.add(instructor);
        instructor.getSesiones().add(this);
    }

    public void removeInstructor(Instructor instructor) {
        instructores.remove(instructor);
        instructor.getSesiones().remove(this);
    }

    public void clearInstructores() {
        // Save a temporary copy to avoid concurrent modification
        List<Instructor> tempInstructores = new ArrayList<>(this.instructores);
        for (Instructor instructor : tempInstructores) {
            removeInstructor(instructor);
        }
    }
}