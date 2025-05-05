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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Null
    @Column(length = 128)
    private String uid;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id")
    private TipoDocumento tipoDocumento;

    @Size(max = 30, message = "El numero de documento no puede exceder 30 caracteres")
    @NotNull(message = "El numero de documento no puede ser nulo")
    private String documento;

    private LocalDate fechaExpedicion;

    @NotNull(message = "Primer apellido no puede ser nulo")
    @Size(max = 50, message = "Primer apellido no puede exceder 50 caracteres")
    private String primerApellido;

    @Size(max = 50, message = "Segundo apellido no puede exceder 50 caracteres")
    private String segundoApellido;

    @NotNull(message = "Primer nombre no puede ser nulo")
    @Size(max = 50, message = "Primer nombre no puede exceder 50 caracteres")
    private String primerNombre;

    @Size(max = 50, message = "Segundo nombre no puede exceder 50 caracteres")
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

    @Null
    @Pattern(regexp = "^[0-9]{15}$", message = "El telefono debe contener 15 digitos")
    private String telefono;

    @Null
    @Size(max = 200, message = "El correo no puede exceder 200 caracteres")
    @Email(message = "El correo debe tener un formato válido")
    private String correoPersonal;

    @OneToMany(mappedBy = "usuario")
    private List<HistoricoUsuario> historicoUsuarios;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Instructor instructor;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Administrador administrador;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Participante participante;

    public String getNombreCompleto() {
        return primerNombre + " " + segundoNombre + " " + primerApellido + " " + segundoApellido;
    }
}
