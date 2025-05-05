package com.example.demo.DTO.response;

import com.example.demo.entities.ProgramaAcademico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaAcademicoDTO implements IResponseDTO<ProgramaAcademico> {
    private Long id;
    private String nombre;
    private String codigo;
    @Override
    public void parseFromEntity(ProgramaAcademico entity) {
    	 this.id = entity.getId();
    	 this.nombre = entity.getNombre();
    	 this.codigo = entity.getCodigo();
    }
}
