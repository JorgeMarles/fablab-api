package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.SemilleroDTO;
import com.example.demo.services.SemilleroService;

@RestController
@RequestMapping("/semilleros")
public class SemilleroController {
    
    @Autowired
    private SemilleroService semilleroService;

    @GetMapping("/")
    public ResponseEntity<List<SemilleroDTO>> listar() {
        return ResponseEntity.ok().body(semilleroService.listar());
    }
}
