package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.SesionDTO;
import com.example.demo.services.SesionService;

@RestController
@RequestMapping("/sesiones")
public class SesionController {
    
    @Autowired
    private SesionService sesionService;

    @GetMapping("/{id}/")
    public ResponseEntity<SesionDTO> buscarPorId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(sesionService.obtenerPorIdDto(id));
    }
}
