package com.example.demo.DTO.request;


import com.example.demo.entities.Motivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroIngresoDTO {
    private Motivo motivo;
    private Long id_oferta_formacion;
    private Long id_institucion;
    private String nombre_institucion;
    private Long id_programa_academico;
    private Long id_sala;
    private String materia;
    private Long id_semillero;
    private String nombre_semillero;
    private String siglas_semillero;
    private Long id_cargo;
    private String asociacion;
}
