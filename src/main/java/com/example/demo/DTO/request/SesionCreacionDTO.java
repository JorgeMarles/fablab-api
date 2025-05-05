package com.example.demo.DTO.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionCreacionDTO {
    private Long id; //Objeto porque puede ser null
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private int id_sala;
    private List<Integer> instructores; //Lista de ids de instructores
}
