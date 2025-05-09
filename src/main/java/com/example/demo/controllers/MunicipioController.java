package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.MunicipioDTO;
import com.example.demo.services.MunicipioService;

@RestController
@RequestMapping("/municipios")
public class MunicipioController {
    
    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/")
    public ResponseEntity<List<MunicipioDTO>> listar(){
        return ResponseEntity.ok().body(municipioService.listar());
    }
}
