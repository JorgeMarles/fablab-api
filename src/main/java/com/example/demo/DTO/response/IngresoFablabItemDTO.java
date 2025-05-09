package com.example.demo.DTO.response;


import com.example.demo.entities.Motivo;
import com.example.demo.entities.RegistroIngreso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoFablabItemDTO implements IResponseDTO<RegistroIngreso> {

  private Long id;
  private String tiempo;
  private String nombre;
  private Motivo motivo;

  @Override
  public void parseFromEntity(RegistroIngreso entity) {
    this.id = entity.getId();
    this.tiempo = entity.getTiempo().toString();
    this.nombre = entity.getUsuario().getNombreCompleto();
    this.motivo = entity.getMotivo();
  }

}
