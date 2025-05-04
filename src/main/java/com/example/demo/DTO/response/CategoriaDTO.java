package com.example.demo.DTO.response;

import com.example.demo.entities.CategoriaOferta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements IResponseDTO<CategoriaOferta> {

    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(CategoriaOferta entity) {
    	if (entity != null) {
            this.id = entity.getId();
            this.nombre = entity.getNombre();
        }
    }
    

}