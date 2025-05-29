package com.example.demo.DTO.response;

import com.example.demo.entities.Participante;
import com.example.demo.services.ReporteService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteParticipanteDTO implements IReporteDTO {
    private String id_tipo_documento;
    private String num_documento;
    private String fecha_expedicion;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private Integer id_sexo_biologico;
    private Long id_estado_civil;
    private String fecha_nacimiento;
    private String id_pais;
    private String id_municipio;
    private String telefono_contacto;
    private String email_personal;
    private String email_institucional;
    private String direccion_institucional;

    public ReporteParticipanteDTO(Participante participante) {
        this.id_tipo_documento = participante.getUsuario().getTipoDocumento() != null ? 
            participante.getUsuario().getTipoDocumento().getSiglas() : "";
        this.num_documento = participante.getUsuario().getDocumento() != null ? 
            participante.getUsuario().getDocumento() : "";
        this.fecha_expedicion = participante.getUsuario().getFechaExpedicion() != null ? 
            participante.getUsuario().getFechaExpedicion().format(ReporteService.DATE_FORMATTER) : "";
        this.primer_nombre = participante.getUsuario().getPrimerNombre() != null ? 
            participante.getUsuario().getPrimerNombre() : "";
        this.segundo_nombre = participante.getUsuario().getSegundoNombre() != null ? 
            participante.getUsuario().getSegundoNombre() : "";
        this.primer_apellido = participante.getUsuario().getPrimerApellido() != null ? 
            participante.getUsuario().getPrimerApellido() : "";
        this.segundo_apellido = participante.getUsuario().getSegundoApellido() != null ? 
            participante.getUsuario().getSegundoApellido() : "";
        this.id_sexo_biologico = participante.getUsuario().getSexo() != null ? 
            participante.getUsuario().getSexo().getValue() : 0;
        this.id_estado_civil = participante.getEstadoCivil() != null ? 
            participante.getEstadoCivil().getId() : 0;
        this.fecha_nacimiento = participante.getUsuario().getFechaNacimiento() != null ? 
            participante.getUsuario().getFechaNacimiento().format(ReporteService.DATE_FORMATTER) : "";
        this.id_pais = participante.getUsuario().getPais() != null ? 
            participante.getUsuario().getPais().getCodigo() : "";
        this.id_municipio = participante.getUsuario().getMunicipio() != null ? 
            participante.getUsuario().getMunicipio().getCodigo() : "";
        this.telefono_contacto = participante.getUsuario().getTelefono() != null ? 
            participante.getUsuario().getTelefono() : "";
        this.email_personal = participante.getUsuario().getCorreoPersonal() != null ? 
            participante.getUsuario().getCorreoPersonal() : "";
        this.email_institucional = participante.getCorreoInstitucional() != null ? 
            participante.getCorreoInstitucional() : "";
        this.direccion_institucional = participante.getDireccionInstitucional() != null ? 
            participante.getDireccionInstitucional() : "";
    }
}
