package com.example.demo.DTO.request;

import java.lang.reflect.Field;

import com.example.demo.utils.ChangeMap;
import com.example.demo.utils.Mappeable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGoogleDTO {
    private String uid;
    @Mappeable(mapsTo = "correoPersonal")
    private String correo;
    @Mappeable(mapsTo = "nombre")
    private String nombre;

    public void registerChanges(ChangeMap map) throws Exception {
        for (Field field : this.getClass().getDeclaredFields()) {
            Mappeable mappeable = field.getAnnotation(Mappeable.class);
            if (mappeable != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if (mappeable.mapsTo().equals("nombre")) {
                        String[] nombres = ((String) value).split(" ");
                        map.registerNewValue("primerNombre", nombres[0]);
                        if(nombres.length == 2){
                            map.registerNewValue("primerApellido", nombres[1]);
                        }else if(nombres.length > 1){
                            map.registerNewValue("segundoNombre", nombres[1]);
                        }
                        if(nombres.length > 2){
                            map.registerNewValue("primerApellido", nombres[2]);
                        }
                        if(nombres.length > 3){
                            map.registerNewValue("segundoApellido", nombres[3]);
                        }
                    } else {
                        map.registerNewValue(mappeable.mapsTo(), value);
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }
}
