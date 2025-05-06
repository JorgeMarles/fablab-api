package com.example.demo.DTO.response;

import com.example.demo.entities.Institucion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitucionDTO implements IResponseDTO<Institucion>{

    private Long id;
    private String nombre;
    private String tipoInstitucion;
    @Override
    public void parseFromEntity(Institucion entity) {
    	this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.tipoInstitucion = entity.getTipoInstitucion().getNombre();
    }


}
