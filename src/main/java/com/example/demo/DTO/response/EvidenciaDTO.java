package com.example.demo.DTO.response;

import com.example.demo.entities.Evidencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaDTO implements IResponseDTO<Evidencia> {
  private Long id;
  private String nombre;
  private InstructorItemDTO instructor;
  private String url;
  private String nombre_archivo;

  @Override
  public void parseFromEntity(Evidencia entity) {
    this.id = entity.getId();
    this.nombre = entity.getNombre();
    this.instructor = new InstructorItemDTO();
    this.instructor.parseFromEntity(entity.getInstructor());
    this.url = entity.getArchivo().getUrl();
    this.nombre_archivo = entity.getArchivo().getNombre();
  }
}
