package com.example.demo.DTO.response;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Sesion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionItemDTO implements IResponseDTO<Sesion> {

    private Long id;
    private String nombre;
    private String fecha;
    private String inicio;
    private String fin;
    private SalaDTO sala;
    private List<InstructorItemDTO> instructores = new ArrayList<>();

    @Override
    public void parseFromEntity(Sesion entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.fecha = entity.getFecha().toString();
        this.inicio = entity.getInicio().toString();
        this.fin = entity.getFin().toString();
        this.sala = new SalaDTO();
        this.sala.parseFromEntity(entity.getSala());
        this.instructores = entity.getInstructores().stream().map(instructor -> {
            InstructorItemDTO dto = new InstructorItemDTO();
            dto.parseFromEntity(instructor);
            return dto;
        }).toList();
    }
}
