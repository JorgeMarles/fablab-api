package com.example.demo.entities;

public enum Sexo {
    MASCULINO(1),
    FEMENINO(2);

    private final int value;
    Sexo(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
