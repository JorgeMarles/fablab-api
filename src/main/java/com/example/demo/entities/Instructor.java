package com.example.demo.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.utils.ChangeMap;
import com.example.demo.utils.Observable;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"sesiones", "evidencias", "certificados"})
@EqualsAndHashCode(exclude = {"sesiones", "evidencias", "certificados"})
@Table(name = "instructor")
public class Instructor {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Observable(tipo = TipoDato.STRING)
    private String direccion;

    @Observable(tipo = TipoDato.STRING)
    private String entidad;
    
    @ManyToOne
    @JoinColumn(name = "modalidad_id")
    @Observable(tipo = TipoDato.ENTITY)
    private Modalidad modalidad;

    @Observable(tipo = TipoDato.BOOLEAN)
    private Boolean activo;

    @ManyToMany
    @JoinTable(name = "instructor_sesion",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "sesion_id"))
    private List<Sesion> sesiones = new ArrayList<>();;

    @OneToMany(mappedBy = "instructor")
    private List<Evidencia> evidencias = new ArrayList<>();;

    @OneToMany(mappedBy = "instructor")
    private List<Certificado> certificados = new ArrayList<>();;

    public void registerValues(ChangeMap map, boolean callSuper) throws Exception{
        if(callSuper){
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
