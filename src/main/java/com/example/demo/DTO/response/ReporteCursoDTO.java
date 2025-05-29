package com.example.demo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCursoDTO implements IReporteDTO {
    public String codigo_curso;
    public String nombre_curso;
    public Integer id_cine_campo_detallado;
    public String es_extension;
    public String activo;
}
