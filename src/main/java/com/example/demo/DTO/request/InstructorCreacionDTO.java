package com.example.demo.DTO.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCreacionDTO {
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private int id_tipo_documento;
    private String documento;
    private LocalDate fecha_expedicion;
    private String sexo;
    private LocalDate fecha_nacimiento;
    private int id_pais;
    private int id_municipio;
    private String telefono;
    private String correo_personal;
    private String direccion;
    private String entidad;
    private int id_modalidad;
}
