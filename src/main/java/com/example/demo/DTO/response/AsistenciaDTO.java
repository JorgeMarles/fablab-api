package com.example.demo.DTO.response;

import com.example.demo.entities.Asistencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaDTO implements IResponseDTO<Asistencia>{
    private Long id;
    private ParticipanteItemDTO participante;
    private boolean asistio;
    @Override
    public void parseFromEntity(Asistencia entity) {
        this.id = entity.getId();
        this.participante = new ParticipanteItemDTO();
        this.participante.parseFromEntity(entity.getParticipante());
        this.asistio = entity.isAsistio();
    }
}
