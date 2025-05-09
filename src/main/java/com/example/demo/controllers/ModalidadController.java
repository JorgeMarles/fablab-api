package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.ModalidadDTO;
import com.example.demo.services.ModalidadService;

@RestController
@RequestMapping("/modalidades")
public class ModalidadController {
    
    @Autowired
    private ModalidadService modalidadService;

    @GetMapping("/")
    public ResponseEntity<List<ModalidadDTO>> listar(){
        return ResponseEntity.ok().body(modalidadService.listar());
    }
}
