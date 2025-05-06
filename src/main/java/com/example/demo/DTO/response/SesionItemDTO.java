package com.example.demo.DTO.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.entities.Sesion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionItemDTO implements IResponseDTO<Sesion>{

    private Long id;
    private String nombre;
     private LocalDate fecha;
	private LocalTime inicio;
    private LocalTime fin;
    private SalaDTO sala;
    @Override
    public void parseFromEntity(Sesion entity) {
    	this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.fecha = entity.getFecha();
        this.inicio = entity.getInicio();
        this.fin = entity.getFin();
        this.sala = new SalaDTO();
        this.sala.parseFromEntity(entity.getSala());
    }
}
