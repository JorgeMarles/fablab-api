package com.example.demo.entities;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Observable(tipo = TipoDato.ENTITY)
    private TipoDocumento tipoDocumento;

    @Size(max = 30, message = "El numero de documento no puede exceder 30 caracteres")
    @NotNull(message = "El numero de documento no puede ser nulo")
    @Observable(tipo = TipoDato.STRING)
    private String documento;

    @Observable(tipo = TipoDato.DATE)
    private LocalDate fechaExpedicion;

    @NotNull(message = "Primer apellido no puede ser nulo")
    @Size(max = 50, message = "Primer apellido no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String primerApellido;

    @Size(max = 50, message = "Segundo apellido no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String segundoApellido;

    @NotNull(message = "Primer nombre no puede ser nulo")
    @Size(max = 50, message = "Primer nombre no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String primerNombre;

    @Size(max = 50, message = "Segundo nombre no puede exceder 50 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String segundoNombre;

    @Enumerated(EnumType.STRING)
    @Observable(tipo = TipoDato.ENUM)
    private Sexo sexo;

    @Observable(tipo = TipoDato.DATE)
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    @Observable(tipo = TipoDato.ENTITY)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @Observable(tipo = TipoDato.ENTITY)
    private Municipio municipio;

    @Null
    @Pattern(regexp = "^[0-9]{15}$", message = "El telefono debe contener 15 digitos")
    @Observable(tipo = TipoDato.STRING)
    private String telefono;

    @Null
    @Size(max = 200, message = "El correo no puede exceder 200 caracteres")
    @Email(message = "El correo debe tener un formato válido")
    @Observable(tipo = TipoDato.STRING)
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

    public void registerValues(ChangeMap map) throws Exception {
        for (Field field : this.getClass().getDeclaredFields()) {
            Observable observable = field.getAnnotation(Observable.class);
            if (observable != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if(observable.tipo() == TipoDato.ENTITY){
                        value = value.getClass().getMethod("getId").invoke(value);
                    }
                    map.registerOldValue(field.getName(), value, observable.tipo());
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public static List<String> getFields(){
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
