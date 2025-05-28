package com.example.demo.entities;

public enum AsistenciaEstado {
    PRESENTE("Presente"),
    AUSENTE("Ausente"),
    PENDIENTE("Pendiente");

    private String nombre;

    AsistenciaEstado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
