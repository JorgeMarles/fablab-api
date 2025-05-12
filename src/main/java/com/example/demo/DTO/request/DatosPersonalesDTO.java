package com.example.demo.DTO.request;

import java.lang.reflect.Field;
import java.time.LocalDate;

import com.example.demo.entities.Sexo;
import com.example.demo.utils.ChangeMap;
import com.example.demo.utils.Mappeable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosPersonalesDTO {
    @Mappeable(mapsTo = "primerNombre")
    private String primer_nombre;
    @Mappeable(mapsTo = "segundoNombre")
    private String segundo_nombre;
    @Mappeable(mapsTo = "primerApellido")
    private String primer_apellido;
    @Mappeable(mapsTo = "segundoApellido")
    private String segundo_apellido;
    @Mappeable(mapsTo = "tipoDocumento")
    private Long id_tipo_documento;
    @Mappeable(mapsTo = "documento")
    private String documento;
    @Mappeable(mapsTo = "fechaExpedicion")
    private LocalDate fecha_expedicion;
    @Mappeable(mapsTo = "sexo")
    private Sexo sexo;
    @Mappeable(mapsTo = "fechaNacimiento")
    private LocalDate fecha_nacimiento;
    @Mappeable(mapsTo = "pais")
    private Long id_pais;
    @Mappeable(mapsTo = "municipio")
    private Long id_municipio;
    @Mappeable(mapsTo = "telefono")
    private String telefono;
    @Mappeable(mapsTo = "correoPersonal")
    private String correo_personal;
    @Mappeable(mapsTo = "correoInstitucional")
    private String correo_institucional;
    @Mappeable(mapsTo = "direccionInstitucional")
    private String direccion_institucional;
    @Mappeable(mapsTo = "poblacionEspecial")
    private Long id_poblacion_especial;
    @Mappeable(mapsTo = "estadoCivil")
    private Long id_estado_civil;
    @Mappeable(mapsTo = "direccion")
    private String direccion;
    @Mappeable(mapsTo = "entidad")
    private String entidad;
    @Mappeable(mapsTo = "modalidad")
    private Long id_modalidad;

    private String password;

    public void registerChanges(ChangeMap map) throws Exception{
        for (Field field : this.getClass().getDeclaredFields()) {
            Mappeable mappeable = field.getAnnotation(Mappeable.class);
            if (mappeable != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    map.registerNewValue(field.getName(), value);
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }
}
