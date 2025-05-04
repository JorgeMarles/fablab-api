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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }

    
}
