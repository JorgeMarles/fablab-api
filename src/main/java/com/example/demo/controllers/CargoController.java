package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.CargoDTO;
import com.example.demo.services.CargoService;

@RestController
@RequestMapping("/cargos")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;

    @GetMapping("/")
    public ResponseEntity<List<CargoDTO>> listar(){
        return ResponseEntity.ok().body(cargoService.listar());
    }
}
