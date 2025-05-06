package com.example.demo.utils;

import com.example.demo.entities.TipoDato;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase para manejar metadatos e información de un campo.
 * 
 * <p>
 * Guarda el valor de la entidad, el valor del Dto, y el tipo de dato
 */
@Data
@AllArgsConstructor
public class FieldMetadata {
    private Object oldValue;
    private Object newValue;
    private final TipoDato type;

    /**
     * Verifica si el valor en realidad cambió, aplicando comparacoones según el
     * tipo de dato.
     * 
     * @return {@code true} si ambos valores son iguales
     */
    public boolean areEqual() {
        if (oldValue == null && newValue == null) {
            return true;
        }

        if (oldValue == null || newValue == null) {
            return false;
        }

        return switch (type) {
            case STRING -> oldValue.equals(newValue);
            case INT, DOUBLE -> compareNumbers(oldValue, newValue);
            case DATE -> oldValue.equals(newValue);
            case BOOLEAN -> oldValue.equals(newValue);
            case ENTITY ->
                compareEntities(oldValue, newValue);
            case ENUM -> compareEnums(oldValue, newValue);
        };
    }

    private boolean compareEnums(Object oldValue, Object newValue) {
        return oldValue == newValue;
    }

    private boolean compareNumbers(Object val1, Object val2) {
        return ((Number) val1).doubleValue() == ((Number) val2).doubleValue();
    }

    private boolean compareEntities(Object val1, Object val2) {
        try {
            Long id1 = (Long) val1.getClass().getMethod("getId").invoke(val1);
            Long id2;
            if (val2 instanceof Long) {
                id2 = (Long) val2;
            } else {
                id2 = (Long) val2.getClass().getMethod("getId").invoke(val2);
            }
            return id1.equals(id2);
        } catch (Exception e) {
            return false;
        }
    }
}
