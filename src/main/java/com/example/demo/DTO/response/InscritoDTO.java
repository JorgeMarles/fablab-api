package com.example.demo.DTO.response;

import com.example.demo.entities.Inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscritoDTO implements IResponseDTO<Inscripcion>{
    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(Inscripcion entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEntity'");
    }


}
