package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.DatosPersonalesDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/datos-personales")
@Slf4j
public class DatosPersonalesController {

    // TODO desarrollar controller
    @GetMapping("/")
    public ResponseEntity<?> obtenerDatosPersonales() {
        // Lógica para registrar los datos personales
        // ...
        Long userId = 1L; // TODO sacarlo de la sesion
        log.info("Consultando datos personales para el usuario con ID: {}", userId);    
        return ResponseEntity.ok("Aquí irian los datos personales");
    }

    @PostMapping("/")
    public ResponseEntity<?> registrarDatosPersonales(
            @RequestBody DatosPersonalesDTO datosPersonales) {
        // Lógica para registrar los datos personales
        // ...
        Long userId = 1L; // TODO sacarlo de la sesion
        log.info("Registrando datos personales para el usuario con ID: {}", userId);    
        return ResponseEntity.created(null).body("Guardado(n't) correctamente");
    }

}
