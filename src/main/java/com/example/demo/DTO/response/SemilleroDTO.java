package com.example.demo.DTO.response;

import com.example.demo.entities.Semillero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemilleroDTO implements IResponseDTO<Semillero>{
    private Long id;
    private String nombre;
    private String siglas;
    @Override
    public void parseFromEntity(Semillero entity) {
           this.id = entity.getId();
           this.nombre = entity.getNombre();
           this.siglas = entity.getSiglas();
    }
    
}
