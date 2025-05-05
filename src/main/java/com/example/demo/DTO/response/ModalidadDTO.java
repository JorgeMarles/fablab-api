package com.example.demo.DTO.response;

import com.example.demo.entities.Modalidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadDTO implements IResponseDTO<Modalidad>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Modalidad entity) {
    	 this.id = entity.getId();
    	 this.nombre = entity.getNombre();
    }


}
