package com.example.demo.DTO.response;

import com.example.demo.entities.Instructor;
import com.example.demo.services.ReporteService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportePracticanteDTO implements IReporteDTO {
private Integer año;
private Integer semestre;
private String id_tipo_documento;
private String num_documento;
private String fecha_expedicion;
private String primer_nombre;
private String segundo_nombre;
private String primer_apellido;
private String segundo_apellido;
private Integer id_sexo_biologico;
private String fecha_nacimiento;
private String id_pais;
private String municipio_nacimiento;
private String direccion_residencia;
private String telefono_contacto;
private String email_personal;
private String entidad_donde_realiza_practica_o_pasantia;
private String modalidad;

    public ReportePracticanteDTO(Instructor instructor){
        this.año = instructor.getUsuario().getFechaExpedicion() != null ?
            instructor.getUsuario().getFechaExpedicion().getYear() : 0;
        this.semestre = instructor.getUsuario().getFechaExpedicion() != null ?
            (instructor.getUsuario().getFechaExpedicion().getMonthValue() <= 6 ? 1 : 2) : 0;
        this.id_tipo_documento = instructor.getUsuario().getTipoDocumento() != null ? 
            instructor.getUsuario().getTipoDocumento().getSiglas() : "";
        this.num_documento = instructor.getUsuario().getDocumento() != null ?
            instructor.getUsuario().getDocumento() : "";
        this.fecha_expedicion = instructor.getUsuario().getFechaExpedicion() != null ?
            instructor.getUsuario().getFechaExpedicion().format(ReporteService.DATE_FORMATTER) : "";
        this.primer_nombre = instructor.getUsuario().getPrimerNombre() != null ?
            instructor.getUsuario().getPrimerNombre() : "";
        this.segundo_nombre = instructor.getUsuario().getSegundoNombre() != null ?
            instructor.getUsuario().getSegundoNombre() : "";
        this.primer_apellido = instructor.getUsuario().getPrimerApellido() != null ?
            instructor.getUsuario().getPrimerApellido() : "";
        this.segundo_apellido = instructor.getUsuario().getSegundoApellido() != null ?
            instructor.getUsuario().getSegundoApellido() : "";
        this.id_sexo_biologico = instructor.getUsuario().getSexo() != null ?
            instructor.getUsuario().getSexo().getValue() : 0;
        this.fecha_nacimiento = instructor.getUsuario().getFechaNacimiento() != null ?
            instructor.getUsuario().getFechaNacimiento().format(ReporteService.DATE_FORMATTER) : "";
        this.id_pais = instructor.getUsuario().getPais() != null ?
            instructor.getUsuario().getPais().getCodigo() : "";
        this.municipio_nacimiento = instructor.getUsuario().getMunicipio() != null ?
            instructor.getUsuario().getMunicipio().getCodigo() : "";
        this.direccion_residencia = instructor.getDireccion() != null ?
            instructor.getDireccion() : "";
        this.telefono_contacto = instructor.getUsuario().getTelefono() != null ?
            instructor.getUsuario().getTelefono() : "";
        this.email_personal = instructor.getUsuario().getCorreoPersonal() != null ?
            instructor.getUsuario().getCorreoPersonal() : "";
        this.entidad_donde_realiza_practica_o_pasantia = instructor.getEntidad() != null ?
            instructor.getEntidad() : "";
        this.modalidad = instructor.getModalidad() != null ?
            instructor.getModalidad().getNombre() : "";

    }
}
