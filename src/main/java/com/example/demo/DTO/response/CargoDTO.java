package com.example.demo.DTO.response;

import com.example.demo.entities.Cargo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO implements IResponseDTO<Cargo> {
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Cargo entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }

}
