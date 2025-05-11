package com.example.demo.DTO.response;

import com.example.demo.entities.PlantillaCertificado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaDTO implements IResponseDTO<PlantillaCertificado> {
    private Long id;
    private String nombre;
    private String url;

    @Override
    public void parseFromEntity(PlantillaCertificado entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.url = entity.getArchivo().getUrl();
    }

}
