package com.example.demo.DTO.response;

import com.example.demo.entities.Pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisDTO implements IResponseDTO<Pais>{
    
    private Long id;
    private String nombre;
    private String codigo;
    @Override
    public void parseFromEntity(Pais entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
    

}
