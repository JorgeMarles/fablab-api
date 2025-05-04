package com.example.demo.DTO.response;

import java.time.LocalDate;

import com.example.demo.entities.Instructor;
import com.example.demo.entities.Sexo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDetalleDTO implements IResponseDTO<Instructor>{
    
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
    private String direccion;
    private String entidad;
    private ModalidadDTO modalidad;
    private boolean activo;
    @Override
    public void parseFromEntity(Instructor entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


}
