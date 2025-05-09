package com.example.demo.DTO.response;

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
    private String fecha;
	private String inicio;
    private String fin;
    private SalaDTO sala;
    private List<InstructorItemDTO> instructores;
    private List<AsistenciaDTO> participantes;
    private List<EvidenciaDTO> evidencias;
    @Override
    public void parseFromEntity(Sesion entity) {
		this.id = entity.getId();
		this.fecha = entity.getFecha().toString();
		this.nombre = entity.getNombre();
		this.inicio = entity.getInicio().toString();
		this.fin = entity.getFin().toString();
		this.sala = new SalaDTO();
		this.sala.parseFromEntity(entity.getSala());
		
		this.instructores = entity.getInstructores().stream()
		        .map(instructor -> {
		            InstructorItemDTO instructorDTO = new InstructorItemDTO();
		            instructorDTO.parseFromEntity(instructor);
		            return instructorDTO;
		        })
		        .toList();
		
		this.participantes = entity.getAsistencias().stream()
	            .map(participante -> {
	                AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
	                asistenciaDTO.parseFromEntity(participante);
	                return asistenciaDTO;
	            })
	            .toList();

	    this.evidencias = entity.getEvidencias().stream()
	            .map(evidencia -> {
	                EvidenciaDTO evidenciaDTO = new EvidenciaDTO();
	                evidenciaDTO.parseFromEntity(evidencia);
	                return evidenciaDTO;
	            })
	            .toList();
    }
}
