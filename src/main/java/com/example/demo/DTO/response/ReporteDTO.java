package com.example.demo.DTO.response;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private String nombre;
    private List<IReporteDTO> datos = new LinkedList<>();
}
