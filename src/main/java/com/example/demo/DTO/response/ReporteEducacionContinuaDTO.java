package com.example.demo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEducacionContinuaDTO implements IReporteDTO {
    private Integer año;
    private Integer semestre;
    private String codigo_curso;
    private Integer num_horas;
    private Long id_tipo_curso_extension;
    private Integer valor_curso;
    private String id_tipo_documento;
    private String num_documento;
    private Long id_tipo_benef_extension;
    private Integer cantidad_beneficiarios;
}
