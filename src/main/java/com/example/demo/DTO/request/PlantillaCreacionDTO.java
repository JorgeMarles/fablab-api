package com.example.demo.DTO.request;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.services.FileService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaCreacionDTO {
    private String nombre;
    private MultipartFile archivo;

    public void setArchivo(MultipartFile archivo) {
        String[] validExtensions = { ".pdf" };
        FileService.validateExtension(archivo.getOriginalFilename(), validExtensions);
        this.archivo = archivo;
    }
}
