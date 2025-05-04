package com.example.demo.DTO.response;

import com.example.demo.entities.PoblacionEspecial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoblacionEspecialDTO implements IResponseDTO<PoblacionEspecial>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(PoblacionEspecial entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
}
