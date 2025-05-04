package com.example.demo.DTO.response;

import java.time.LocalDate;

import com.example.demo.entities.RegistroIngreso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoFablabItemDTO implements IResponseDTO<RegistroIngreso>{

    private Long id;
    private LocalDate fecha;
    private String nombre;
    private String motivo;
    @Override
    public void parseFromEntity(RegistroIngreso entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEntity'");
    }


}
