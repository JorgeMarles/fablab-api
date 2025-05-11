package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.example.demo.entities.TipoDato;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapa de cambios en campos de entidades con Dto
 * <p>Se debe registar el valor primero de la entidad y luego del Dto
 * 
 * <p>Funciona registrando el valor de la entidad y del Dto, para posterior comparación
 * 
 * @author Jorge Marles
 */
@Data
@NoArgsConstructor
@Slf4j
public class ChangeMap {
    private final Map<String, FieldChangeMetadata> fields = new HashMap<>();
    
    /**
     * Adaptación para ejecutar {@code forEach()} en la clase
     * @param action
     */
    public void forEach(BiConsumer<String, FieldChangeMetadata> action) {
        fields.forEach(action);
        new ChangeMap();
    }

    /**
     * Registra los valores antiguos de la clase.
     * <p>Está pensado para ser llamado antes de {@code registerNewValue()}
     * @param field el nombre del campo a registrar
     * @param value el valor de la entidad
     * @param type {@code Enum TipoDato}, el tipo de dato del campo
     */
    public void registerOldValue(String field, Object value, TipoDato type){
        if(fields.containsKey(field)){
            throw new RuntimeException("El campo "+field+" ya estaba registrado en esta entidad, esto no debería pasar porque no compilaría.");
        }
        FieldChangeMetadata fm = new FieldChangeMetadata(value, null, type);
        fields.put(field, fm);
    }  

    /**
     * Registra los nuevos valores, eliminando los campos que no hayan cambiado.
     * <p>Está pensado para ser llamado después de {@code registerOldValue()}
     * @param field el nombre del campo a verificar
     * @param value el valor que está en el Dto
     */
    public void registerNewValue(String field, Object value){
        if(value == null){
            return;
        }
        if(!fields.containsKey(field)){
            log.error("Error: propiedad "+field+" no registrada. Ignorando...");
            return;
        }
        FieldChangeMetadata fm = fields.get(field);
        fm.setNewValue(value);
        if(fm.areEqual()){
            fields.remove(field);
        }
    }
}
