package com.example.demo.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entities.Sesion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionDTO implements IResponseDTO<Sesion>{

    private Long id;
    private String nombre;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private SalaDTO sala;
    private List<InstructorItemDTO> instructores;
    private List<AsistenciaDTO> participantes;
    private List<EvidenciaDTO> evidencias;
    @Override
    public void parseFromEntity(Sesion entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }

    
}
