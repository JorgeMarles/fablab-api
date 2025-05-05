package com.example.demo.DTO.response;

import com.example.demo.entities.Inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscritoDTO implements IResponseDTO<Inscripcion>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Inscripcion entity) {
    	 this.id = entity.getId();
    	 this.nombre = entity.getParticipante().getUsuario().getNombreCompleto();
    }


}
