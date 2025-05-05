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
    	this.id = entity.getId();
        this.primer_nombre = entity.getUsuario().getPrimerNombre();
        this.segundo_nombre = entity.getUsuario().getSegundoNombre();
        this.primer_apellido = entity.getUsuario().getPrimerApellido();
        this.segundo_apellido = entity.getUsuario().getSegundoApellido();
        this.correo_personal = entity.getUsuario().getCorreoPersonal();
        this.tipo_documento = new TipoDocumentoDTO();
        this.tipo_documento.parseFromEntity(entity.getUsuario().getTipoDocumento());
        this.documento = entity.getUsuario().getDocumento();
        this.fecha_expedicion = entity.getUsuario().getFechaExpedicion();
        this.sexo = entity.getUsuario().getSexo();
        this.fecha_nacimiento = entity.getUsuario().getFechaNacimiento();
        this.pais = new PaisDTO();
        this.pais.parseFromEntity(entity.getUsuario().getPais());
        this.municipio = new MunicipioDTO();
        this.municipio.parseFromEntity(entity.getUsuario().getMunicipio());
        this.telefono = entity.getUsuario().getTelefono();
        this.direccion = entity.getDireccion();	
        this.entidad = entity.getEntidad();
        this.modalidad = new ModalidadDTO();
        this.modalidad.parseFromEntity(entity.getModalidad());
        this.activo = entity.isActivo();
    }


}
