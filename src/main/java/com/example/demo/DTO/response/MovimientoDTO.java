package com.example.demo.DTO.response;

import java.time.LocalDateTime;

import com.example.demo.entities.Movimiento;
import com.example.demo.entities.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO implements IResponseDTO<Movimiento>{
    private Long id;
    private String nombre;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    @Override
    public void parseFromEntity(Movimiento entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


}
