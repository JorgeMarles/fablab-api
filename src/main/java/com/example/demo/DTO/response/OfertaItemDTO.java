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
    @Override
    public void parseFromEntity(OfertaFormacion entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


}
