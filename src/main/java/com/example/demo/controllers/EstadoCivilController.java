package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.EstadoCivilDTO;
import com.example.demo.services.EstadoCivilService;

@RestController
@RequestMapping("/estados-civiles")
public class EstadoCivilController {
    
    @Autowired
    private EstadoCivilService estadoCivilService;

    @GetMapping("/")
    public ResponseEntity<List<EstadoCivilDTO>> listar(){
        return ResponseEntity.ok().body(estadoCivilService.listar());
    }
}
