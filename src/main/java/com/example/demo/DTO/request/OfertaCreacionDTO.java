package com.example.demo.DTO.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.services.FileService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaCreacionDTO {
    private String nombre;
    private String codigo;
    private Integer cine;
    private boolean extension;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Integer horas;
    private Long id_tipo;
    private Long id_categoria;
    private Integer valor;
    private List<Long> tipos_beneficiario;
    private Integer cupo_maximo;
    private MultipartFile pieza_grafica; // La idea es que se haga set y se pase solo este objeto al service
    private List<Long> instituciones;
    private List<SesionCreacionDTO> sesiones;
    private Integer semestre;

    public void setPieza_grafica(MultipartFile pieza_grafica) {
        String[] validExtensions = { ".jpg", ".jpeg", ".png", ".pdf", ".svg", ".bmp", ".gif", ".tiff", ".tif",
                ".webp", ".ico", ".svg" };
        FileService.validateExtension(pieza_grafica.getOriginalFilename(), validExtensions);
        this.pieza_grafica = pieza_grafica;
    }
}
