package com.example.demo.DTO.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaTokenDTO {
    private String token;
    private String expiracion;

    public AsistenciaTokenDTO(String token, LocalDateTime expiracion) {
        this.token = token;
        this.expiracion = expiracion.toString();
    }
}
