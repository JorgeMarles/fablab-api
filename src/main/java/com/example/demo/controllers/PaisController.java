package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.PaisDTO;
import com.example.demo.services.PaisService;

@RestController
@RequestMapping("/paises")
public class PaisController {
    
    @Autowired
    private PaisService paisService;

    @GetMapping("/")
    public ResponseEntity<List<PaisDTO>> listar(){
        return ResponseEntity.ok().body(paisService.listar());
    }
}
