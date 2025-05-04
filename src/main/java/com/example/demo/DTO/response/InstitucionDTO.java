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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEntity'");
    }


}
