package com.example.demo.DTO.response;

import com.example.demo.entities.Municipio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO implements IResponseDTO<Municipio>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Municipio entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
}
