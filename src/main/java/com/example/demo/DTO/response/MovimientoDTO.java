package com.example.demo.DTO.response;

import com.example.demo.entities.Movimiento;
import com.example.demo.entities.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO implements IResponseDTO<Movimiento> {
    private Long id;
    private InstructorItemDTO instructor;
    private String fecha;
    private TipoMovimiento tipo;

    @Override
    public void parseFromEntity(Movimiento entity) {
        this.id = entity.getId();
        this.instructor = new InstructorItemDTO();
        this.instructor.parseFromEntity(entity.getInstructor());
        this.fecha = entity.getFecha().toString();
        this.tipo = entity.getTipoMovimiento();
    }

}
