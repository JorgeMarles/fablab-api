package com.example.demo.DTO.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaCreacionDTO {
    public String nombre;
    public Long id_instructor;
    public MultipartFile archivo;
    public Long id_sesion;
}