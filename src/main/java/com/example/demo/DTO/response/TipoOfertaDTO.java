package com.example.demo.DTO.response;

import com.example.demo.entities.TipoOferta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoOfertaDTO implements IResponseDTO<TipoOferta> {

    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(TipoOferta entity) {
                this.id = entity.getId();
                this.nombre = entity.getNombre();
    }
    
}
