package com.example.demo.DTO.response;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.OfertaFormacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaDetalleDTO implements IResponseDTO<OfertaFormacion>{

    private Long id;
    private String nombre;
    private String codigo;
    private String cine;
    private boolean extension;
    private EstadoOfertaFormacion estado;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int horas;
    private TipoOfertaDTO tipo_oferta;
    private CategoriaDTO categoria;
    private TipoBeneficiarioDTO tipo_beneficiario;
    private int semestre;
    private int valor;
    private String pieza_grafica;
    private InstitucionDTO institucion;
    private List<SesionItemDTO> sesiones;
    private List<InscritoDTO> inscritos;
    @Override
    public void parseFromEntity(OfertaFormacion entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseFromEntity'");
    }


    
}
