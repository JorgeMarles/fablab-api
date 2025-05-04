package com.example.demo.DTO.response;

import java.time.LocalDate;

import com.example.demo.entities.Participante;
import com.example.demo.entities.Sexo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDetalleDTO implements IResponseDTO<Participante> {
    private Long id;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String correo_personal;
    private TipoDocumentoDTO tipo_documento;
    private String documento;
    private LocalDate fecha_expedicion;
    private Sexo sexo;
    private LocalDate fecha_nacimiento;
    private PaisDTO pais;
    private MunicipioDTO municipio;
    private String telefono;
    private EstadoCivilDTO estado_civil;
    private String correo_institucional;
    private String direccion_institucional;
    private PoblacionEspecialDTO poblacion_especial;

    @Override
    public void parseFromEntity(Participante entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }

    
}
