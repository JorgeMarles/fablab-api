package com.example.demo.DTO.response;

import com.example.demo.entities.Participante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteItemDTO implements IResponseDTO<Participante> {
    private Long id;
    private String nombre;
    private String documento;
    private String correo_personal;
    private String correo_institucional;
    @Override
    public void parseFromEntity(Participante entity) {
    	this.id = entity.getId();
		this.nombre = entity.getUsuario().getNombreCompleto();
		this.documento = entity.getUsuario().getDocumento();
    	this.documento = entity.getUsuario().getDocumento();
        this.correo_personal = entity.getUsuario().getCorreoPersonal();
        this.correo_institucional = entity.getCorreoInstitucional();
    }
}
