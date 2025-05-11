package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evidencia")
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToOne
    @JoinColumn(name = "archivo_id", referencedColumnName = "uuid")
    private Archivo archivo;

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    private Sesion sesion;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
