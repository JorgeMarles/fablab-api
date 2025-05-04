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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
    
}
