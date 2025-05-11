package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.DatosPersonalesDTO;

@RestController
@RequestMapping("/datos-personales")
public class DatosPersonalesController {

    // TODO desarrollar controller
    @GetMapping("/")
    public ResponseEntity<?> obtenerDatosPersonales() {
        // Lógica para registrar los datos personales
        // ...
        Long userId = 1L; // TODO sacarlo de la sesion
        DatosPersonalesDTO datosPersonales = new DatosPersonalesDTO();
        return ResponseEntity.ok("Aquí irian los datos personales");
    }

    @PostMapping("/")
    public ResponseEntity<?> registrarDatosPersonales(
            @RequestBody DatosPersonalesDTO datosPersonales) {
        // Lógica para registrar los datos personales
        // ...
        Long userId = 1L; // TODO sacarlo de la sesion

        return ResponseEntity.created(null).body("Guardado(n't) correctamente");
    }

}
