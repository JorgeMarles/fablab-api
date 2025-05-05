package com.example.demo.DTO.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaCreacionDTO {
    private String nombre;
    private MultipartFile archivo;
}
