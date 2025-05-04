package com.example.demo.DTO.response;

import java.time.LocalDateTime;

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
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private SalaDTO sala;
    @Override
    public void parseFromEntity(Sesion entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


}
