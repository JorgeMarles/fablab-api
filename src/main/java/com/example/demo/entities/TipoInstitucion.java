package com.example.demo.entities;

public enum TipoInstitucion {
    UNIVERSIDAD("Universidad"),
    COLEGIO("Colegio"),
    EMPRESA("Empresa"),
    INDEPENDIENTE("Independiente");

    private String nombre;

    TipoInstitucion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}