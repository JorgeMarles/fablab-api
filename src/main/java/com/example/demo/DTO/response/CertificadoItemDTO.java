package com.example.demo.DTO.response;


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
    private String fecha;
    @Override
    public void parseFromEntity(Certificado entity) {
        this.id = entity.getId();
        this.instructor = entity.getInstructor().getUsuario().getNombreCompleto();
        this.oferta_formacion = entity.getOfertaFormacion().getNombre();
        this.fecha = entity.getFecha().toString();
    }
}
