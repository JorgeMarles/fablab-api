package com.example.demo.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String uid;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id")
    private TipoDocumento tipoDocumento;

    private String documento;

    private LocalDate fechaExpedicion;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    private String telefono;
    private String correoPersonal;

    @OneToMany(mappedBy = "usuario")
    private List<HistoricoUsuario> historicoUsuarios;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Instructor instructor;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Administrador administrador;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Participante participante;
}
