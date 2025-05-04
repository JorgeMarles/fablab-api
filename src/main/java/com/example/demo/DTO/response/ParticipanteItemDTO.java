package com.example.demo.DTO.response;

import com.example.demo.entities.Participante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteItemDTO implements IResponseDTO<Participante> {
    private Long id;
    private String nombre;
    private String documento;
    private String correo_personal;
    private String correo_institucional;
    @Override
    public void parseFromEntity(Participante entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
}
