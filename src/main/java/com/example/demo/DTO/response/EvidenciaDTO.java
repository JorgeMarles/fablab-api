package com.example.demo.DTO.response;

import com.example.demo.entities.Evidencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaDTO implements IResponseDTO<Evidencia>{
    private Long id;
    private String nombre;
    private String instructor;
    private String url;
    @Override
    public void parseFromEntity(Evidencia entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
}
