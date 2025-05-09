package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.PoblacionEspecialDTO;
import com.example.demo.services.PoblacionEspecialService;

@RestController
@RequestMapping("/poblaciones-especiales")
public class PoblacionEspecialController {
    
    @Autowired
    private PoblacionEspecialService poblacionEspecialService;

    @GetMapping("/")
    public ResponseEntity<List<PoblacionEspecialDTO>> listar() {
        return ResponseEntity.ok().body(poblacionEspecialService.listar());
    }
}
