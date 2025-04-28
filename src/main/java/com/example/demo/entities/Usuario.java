package com.example.demo.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento")
    private TipoDocumento tipoDocumento;

    private String documento;

    private LocalDate fechaExpedicion;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;

    @ManyToOne
    @JoinColumn(name = "id_genero")
    private Genero genero;

    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    private String telefono;
    private String correoPersonal;

    @OneToMany(mappedBy = "usuario")
    private List<HistoricoUsuario> historicoUsuarios;
}
