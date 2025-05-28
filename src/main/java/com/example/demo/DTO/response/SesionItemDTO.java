package com.example.demo.DTO.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Asistencia;
import com.example.demo.entities.AsistenciaEstado;
import com.example.demo.entities.Sesion;
import com.example.demo.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionItemDTO implements IPersonalizableResponseDTO<Sesion> {

    private Long id;
    private String nombre;
    private String fecha;
    private String inicio;
    private String fin;
    private SalaDTO sala;
    private AsistenciaEstado estado;
    private List<InstructorItemDTO> instructores = new ArrayList<>();

    @Override
    public void personalizeFromEntity(Sesion entity, Usuario usuario) {
        this.parseFromEntity(entity);
        if (usuario != null) {
            Asistencia asistencia = entity.getAsistencias().stream()
                    .filter(a -> a.getParticipante().getId().equals(usuario.getId()))
                    .findFirst()
                    .orElse(null);
            LocalDateTime fin = entity.getFecha().atTime(entity.getFin());
            if (asistencia != null) {
                if (LocalDateTime.now().isAfter(fin)) { // Si la sesión terminó, ya no puede estar pendiente
                    this.estado = asistencia.isAsistio() ? AsistenciaEstado.PRESENTE : AsistenciaEstado.AUSENTE;
                } else {
                    this.estado = AsistenciaEstado.PENDIENTE;
                }
            }
        }
    }

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
