package com.example.demo.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGoogleDTO {
    private String uid;
    private String correo;
    private String nombre;
}
