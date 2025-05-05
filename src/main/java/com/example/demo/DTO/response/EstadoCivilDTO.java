package com.example.demo.DTO.response;

import com.example.demo.entities.EstadoCivil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCivilDTO implements IResponseDTO<EstadoCivil> {
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(EstadoCivil entity) {
    	this.id = entity.getId();
        this.nombre = entity.getNombre();
    }

    
}
