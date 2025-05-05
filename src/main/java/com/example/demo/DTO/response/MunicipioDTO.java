package com.example.demo.DTO.response;

import com.example.demo.entities.Municipio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO implements IResponseDTO<Municipio>{
    private Long id;
    private String nombre;
    private String codigo;
    @Override
    public void parseFromEntity(Municipio entity) {
    	this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.codigo = entity.getCodigo();
    }
}
