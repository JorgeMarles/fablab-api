package com.example.demo.DTO.request;

import java.time.LocalDate;

import com.example.demo.entities.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoCreacionDTO {
    private Long id_instructor;
    private LocalDate fecha;
    private TipoMovimiento tipo;
}
