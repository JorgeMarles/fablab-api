package com.example.demo.entities;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.demo.utils.ChangeMap;
import com.example.demo.utils.Observable;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = { "instructor", "administrador", "participante", "historicoUsuarios", "registroIngresos" })
@EqualsAndHashCode(exclude = { "instructor", "administrador", "participante", "historicoUsuarios", "registroIngresos" })
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = true, unique = true)
    private String uid;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id")
    @Observable(tipo = TipoDato.ENTITY)
    private TipoDocumento tipoDocumento;

    @Size(max = 30, message = "El numero de documento no puede exceder 30 caracteres")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true, unique = true)
    private String documento;

    @Observable(tipo = TipoDato.DATE)
    @Column(nullable = true)
    private LocalDate fechaExpedicion;

    @Size(max = 50, message = "Primer apellido no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true)
    private String primerApellido;

    @Size(max = 50, message = "Segundo apellido no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true)
    private String segundoApellido;

    @NotNull(message = "Primer nombre no puede ser nulo")
    @Size(max = 50, message = "Primer nombre no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String primerNombre;

    @Size(max = 50, message = "Segundo nombre no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true)
    private String segundoNombre;

    @Enumerated(EnumType.STRING)
    @Observable(tipo = TipoDato.ENUM)
    @Column(nullable = true)
    private Sexo sexo;

    @Observable(tipo = TipoDato.DATE)
    @Column(nullable = true)
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    @Observable(tipo = TipoDato.ENTITY)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @Observable(tipo = TipoDato.ENTITY)
    private Municipio municipio;

    @Pattern(regexp = "^[0-9]{1,15}$", message = "El telefono debe contener 15 digitos")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true)
    private String telefono;

    @Size(max = 200, message = "El correo no puede exceder 200 caracteres")
    @Email(message = "El correo debe tener un formato válido")
    @Observable(tipo = TipoDato.STRING)
    @Column(nullable = true, unique = true)
    private String correoPersonal;

    @OneToMany(mappedBy = "usuario")
    private List<HistoricoUsuario> historicoUsuarios = new ArrayList<>();;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Instructor instructor;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Administrador administrador;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Participante participante;

    @OneToMany(mappedBy = "usuario")
    private List<RegistroIngreso> registroIngresos = new ArrayList<>();;

    public String getNombreCompleto() {
        return primerNombre + " " + segundoNombre + " " + primerApellido + " " + segundoApellido;
    }

    public void registerValues(ChangeMap map) throws Exception {
        for (Field field : this.getClass().getDeclaredFields()) {
            Observable observable = field.getAnnotation(Observable.class);
            if (observable != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if (value == null) {
                        continue;
                    }
                    if (observable.tipo() == TipoDato.ENTITY) {
                        value = value.getClass().getMethod("getId").invoke(value);
                    }
                    map.registerOldValue(field.getName(), value, observable.tipo());
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public void setNombreCompleto(String nombreCompleto) {
        String[] nombres = nombreCompleto.split(" ");
        this.primerNombre = nombres[0];
        if (nombres.length == 2) {
            this.primerApellido = nombres[1];
        } else if (nombres.length > 1) {
            this.segundoNombre = nombres[1];
        }
        if (nombres.length > 2) {
            this.primerApellido = nombres[2];
        }
        if (nombres.length > 3) {
            this.segundoApellido = nombres[3];
        }
    }

    public List<String> getRoles() {
        List<String> roles = new LinkedList<>();
        if (instructor != null) {
            roles.add("ROLE_INSTRUCTOR");
        }
        if (administrador != null) {
            roles.add("ROLE_ADMINISTRADOR");
        }
        if (participante != null) {
            roles.add("ROLE_PARTICIPANTE");
        }
        return roles;
    }

    public static List<String> getFields() {
        List<String> fields = new ArrayList<>();
        for (Field field : Usuario.class.getDeclaredFields()) {
            Observable observable = field.getAnnotation(Observable.class);
            if (observable != null) {
                fields.add(field.getName());
            }
        }
        return fields;
    }
}
