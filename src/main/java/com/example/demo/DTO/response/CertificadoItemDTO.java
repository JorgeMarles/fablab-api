package com.example.demo.DTO.response;

import java.time.LocalDate;

import com.example.demo.entities.Certificado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificadoItemDTO implements IResponseDTO<Certificado>{
    private Long id;
    private String instructor;
    private String oferta_formacion;
    private LocalDate fecha;
    @Override
    public void parseFromEntity(Certificado entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }
}
