package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "instructor")
public class Instructor {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String direccion;
    private String entidad;
    
    @ManyToOne
    @JoinColumn(name = "modalidad_id")
    private Modalidad modalidad;

    private boolean activo;

    @ManyToMany
    @JoinTable(name = "instructor_sesion",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "sesion_id"))
    private List<Sesion> sesiones;

    @OneToMany(mappedBy = "instructor")
    private List<Evidencia> evidencias;

    @OneToMany(mappedBy = "instructor")
    private List<Certificado> certificados;
}
