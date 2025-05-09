package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.SalaDTO;
import com.example.demo.services.SalaService;

@RestController
@RequestMapping("/salas")
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @GetMapping("/")
    public ResponseEntity<List<SalaDTO>> listar(){
        return ResponseEntity.ok().body(salaService.listar());
    }
}
