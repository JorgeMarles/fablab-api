package com.example.demo.DTO.response;

import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.OfertaFormacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaItemDTO implements IResponseDTO<OfertaFormacion> {

    private Long id;
    private String nombre;
    private EstadoOfertaFormacion estado;
    private CategoriaDTO categoria;

    @Override
    public void parseFromEntity(OfertaFormacion entity) {
    	this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.estado = entity.getEstado();
        this.categoria = new CategoriaDTO();
        this.categoria.parseFromEntity(entity.getCategoria());
    }
}
