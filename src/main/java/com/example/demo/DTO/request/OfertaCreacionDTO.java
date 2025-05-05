package com.example.demo.DTO.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaCreacionDTO {
    private String nombre;
    private String codigo;
    private String cine;
    private boolean extension;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int horas;
    private int id_tipo;
    private int id_categoria;
    private int valor;
    private int id_tipo_beneficiario;
    private int cupo_maximo;
    private MultipartFile pieza_grafica; //La idea es que se haga set y se pase solo este objeto al service
    private int id_institucion;
    private List<SesionCreacionDTO> sesiones;
    private int semestre;
}
