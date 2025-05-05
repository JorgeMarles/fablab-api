package com.example.demo.DTO.response;

import com.example.demo.entities.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoDTO implements IResponseDTO<TipoDocumento>{

    private Long id;
    private String nombre;
    private String siglas;
    @Override
    public void parseFromEntity(TipoDocumento entity) {
		this.id = entity.getId();
		this.nombre = entity.getNombre();
		this.siglas = entity.getSiglas();
    }
}
