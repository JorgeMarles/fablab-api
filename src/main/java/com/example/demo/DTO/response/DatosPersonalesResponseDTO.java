package com.example.demo.DTO.response;

import com.example.demo.entities.Sexo;
import com.example.demo.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosPersonalesResponseDTO implements IResponseDTO<Usuario> {
    // Usuario
    private Long id;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private TipoDocumentoDTO tipo_documento;
    private String documento;
    private String fecha_expedicion;
    private Sexo sexo;
    private String fecha_nacimiento;
    private PaisDTO pais;
    private MunicipioDTO municipio;
    private String telefono;
    private String correo_personal;
    // Participante
    private String correo_institucional;
    private String direccion_institucional;
    private PoblacionEspecialDTO poblacion_especial;
    private EstadoCivilDTO estado_civil;
    // Instructor
    private String direccion;
    private String entidad;
    private ModalidadDTO modalidad;
    private Boolean activo;

    @Override
    public void parseFromEntity(Usuario entity) {
        this.setId(entity.getId());

        this.setPrimer_nombre(entity.getPrimerNombre());
        this.setSegundo_nombre(entity.getSegundoNombre());
        this.setPrimer_apellido(entity.getPrimerApellido());
        this.setSegundo_apellido(entity.getSegundoApellido());
        this.setDocumento(entity.getDocumento());
        if (entity.getFechaExpedicion() != null)
            this.setFecha_expedicion(entity.getFechaExpedicion().toString());
        this.setSexo(entity.getSexo());
        if (entity.getFechaNacimiento() != null)
            this.setFecha_nacimiento(entity.getFechaNacimiento().toString());
        this.setTelefono(entity.getTelefono());
        this.setCorreo_personal(entity.getCorreoPersonal());
        this.setSexo(entity.getSexo());

        if (entity.getTipoDocumento() != null) {
            this.setTipo_documento(new TipoDocumentoDTO());
            this.getTipo_documento().parseFromEntity(entity.getTipoDocumento());
        }

        if (entity.getPais() != null) {
            this.setPais(new PaisDTO());
            this.getPais().parseFromEntity(entity.getPais());
        }
        if (entity.getMunicipio() != null) {
            this.setMunicipio(new MunicipioDTO());
            this.getMunicipio().parseFromEntity(entity.getMunicipio());
        }

        // Instructor
        if (entity.getInstructor() != null) {
            this.setDireccion(entity.getInstructor().getDireccion());
            this.setEntidad(entity.getInstructor().getEntidad());
            this.setActivo(entity.getInstructor().getActivo());
            if (entity.getInstructor().getModalidad() != null) {
                this.setModalidad(new ModalidadDTO());
                this.getModalidad().parseFromEntity(entity.getInstructor().getModalidad());
            }
        }

        // Participante
        if (entity.getParticipante() != null) {
            this.setCorreo_institucional(entity.getParticipante().getCorreoInstitucional());
            this.setDireccion_institucional(entity.getParticipante().getDireccionInstitucional());
            if (entity.getParticipante().getEstadoCivil() != null) {
                this.setEstado_civil(new EstadoCivilDTO());
                this.getEstado_civil().parseFromEntity(entity.getParticipante().getEstadoCivil());
            }
            if (entity.getParticipante().getPoblacionEspecial() != null) {
                this.setPoblacion_especial(new PoblacionEspecialDTO());
                this.getPoblacion_especial().parseFromEntity(entity.getParticipante().getPoblacionEspecial());
            }
        }

    }
}
