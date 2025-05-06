package com.example.demo.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.utils.ChangeMap;
import com.example.demo.utils.Observable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participante")
public class Participante {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id")
    @Observable(tipo = TipoDato.DOUBLE)
    private EstadoCivil estadoCivil;

    @Null
    @Size(max = 200, message = "El correo no puede exceder 200 caracteres")
    @Email(message = "El correo debe tener un formato válido")
    @Observable(tipo = TipoDato.STRING)
    private String correoInstitucional;

    @Null
    @Size(max = 200, message = "La dirección institucional no puede exceder 200 caracteres")
    @Observable(tipo = TipoDato.STRING)
    private String direccionInstitucional;

    @ManyToOne
    @JoinColumn(name = "poblacion_especial_id")
    @Observable(tipo = TipoDato.ENTITY)
    private PoblacionEspecial poblacionEspecial;

    @OneToMany(mappedBy = "participante")
    private List<Asistencia> asistencias;

    @OneToMany(mappedBy = "participante")
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "participante")
    private List<RegistroIngreso> registroIngresos;

    public void registerValues(ChangeMap map, boolean callSuper) throws Exception {
        if (callSuper) {
            this.getUsuario().registerValues(map);
        }
        for (Field field : this.getClass().getDeclaredFields()) {
            Observable observable = field.getAnnotation(Observable.class);
            if (observable != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
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
