package com.example.demo.DTO.response;

import com.example.demo.entities.Inscripcion;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEducacionContinuaDetalleDTO implements IReporteDTO {
    private String codigo_curso;
    private Long id_tipo_benef_extension;
    private String nombre_del_curso;
    private String tipo_de_curso;
    private String fecha_inicio_del_curso;
    private String tipo_de_documento_del_participante;
    private String numero_de_documento;
    private String nombre_y_apellido_del_participante;
    private String programa;
    private Integer valor_de_curso;
    private Integer numero_de_horas_del_curso;
    private String docente_impartio_el_curso;
    private String tipo_documento_docente;
    private String numero_del_documento;

    public ReporteEducacionContinuaDetalleDTO(OfertaFormacion oferta, Inscripcion inscripcion, Instructor instructor) {
        this.codigo_curso = oferta.getCodigo();
        this.id_tipo_benef_extension = oferta.getTipo().getId();
        this.nombre_del_curso = oferta.getNombre();
        this.tipo_de_curso = oferta.getTipo().getNombre();
        this.fecha_inicio_del_curso = oferta.getFechaInicio().toString();
        if (inscripcion.getParticipante().getUsuario().getTipoDocumento() == null) {
            this.tipo_de_documento_del_participante = "";
        } else {
            this.tipo_de_documento_del_participante = inscripcion.getParticipante().getUsuario().getTipoDocumento()
                    .getFullName();
        }
        this.numero_de_documento = inscripcion.getParticipante().getUsuario().getDocumento() == null ? ""
                : inscripcion.getParticipante().getUsuario().getDocumento();
        this.nombre_y_apellido_del_participante = inscripcion.getParticipante().getUsuario().getNombreCompleto();
        this.programa = inscripcion.getProgramaAcademico() != null ? inscripcion.getProgramaAcademico().getNombre()
                : "";
        this.valor_de_curso = oferta.getValor();
        this.numero_de_horas_del_curso = oferta.getHoras();
        if (instructor != null) {
            this.docente_impartio_el_curso = instructor.getUsuario().getNombreCompleto();
            this.tipo_documento_docente = instructor.getUsuario().getTipoDocumento().getFullName();
            this.numero_del_documento = instructor.getUsuario().getDocumento();
        } else {
            this.docente_impartio_el_curso = "";
            this.tipo_documento_docente = "";
            this.numero_del_documento = "";
        }
    }
}
