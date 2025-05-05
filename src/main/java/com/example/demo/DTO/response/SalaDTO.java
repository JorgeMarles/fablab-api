package com.example.demo.DTO.response;


import com.example.demo.entities.Sala;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO implements IResponseDTO<Sala>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Sala entity) {
		this.id = entity.getId();
		this.nombre = entity.getNombre();
    }
}
