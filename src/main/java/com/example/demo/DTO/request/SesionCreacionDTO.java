package com.example.demo.DTO.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionCreacionDTO {
    private Long id; //Objeto porque puede ser null
    private LocalDate fecha;
    private LocalTime inicio;
    private LocalTime fin;
    private int id_sala;
    private List<Integer> instructores; //Lista de ids de instructores
}
