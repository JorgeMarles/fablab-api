package com.example.demo.DTO.response;

import com.example.demo.entities.TipoBeneficiario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoBeneficiarioDTO implements IResponseDTO<TipoBeneficiario>{

    private Long id;
    private String nombre;
    @Override
    public void parseFromEntity(TipoBeneficiario entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
    }

}
