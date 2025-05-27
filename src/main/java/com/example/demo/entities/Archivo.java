package com.example.demo.entities;

import com.example.demo.services.FileService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    private String uuid;

    private String nombre;

    private String extension;

    @OneToOne(mappedBy = "archivo")
    private Evidencia evidencia;

    @OneToOne(mappedBy = "archivo")
    private PlantillaCertificado plantillaCertificado;

    @OneToOne(mappedBy = "piezaGrafica")
    private OfertaFormacion ofertaFormacion;

    public String getFilename() {
        return uuid + extension;
    }

    public String getUrl() {
        if(this.evidencia != null) {
            return FileService.getFileEndpointFiles("evidencias") + "/" + uuid.toString() + "/";
        }
        if(this.plantillaCertificado != null) {
            return FileService.getFileEndpointFiles("certificados") + "/" + uuid.toString() + "/";
        }
        if(this.ofertaFormacion != null) {
            return FileService.getFileEndpointFiles("piezas") + "/" + uuid.toString() + "/";
        }
        return FileService.getFileEndpointFiles(null) + "/" + uuid.toString() + "/";
    }
}
