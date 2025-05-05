package com.example.demo.DTO.response;

import java.time.LocalDateTime;

import com.example.demo.entities.Motivo;
import com.example.demo.entities.RegistroIngreso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoFablabItemDTO implements IResponseDTO<RegistroIngreso>{

    private Long id;
    private LocalDateTime tiempo;
    private String nombre;
    private Motivo motivo;
    @Override
    public void parseFromEntity(RegistroIngreso entity) {
		this.id = entity.getId();
		this.tiempo = entity.getTiempo();
		this.nombre = entity.getUsuario().getNombreCompleto();
		this.motivo = entity.getMotivo(); // Convert enum to String
    }


}
