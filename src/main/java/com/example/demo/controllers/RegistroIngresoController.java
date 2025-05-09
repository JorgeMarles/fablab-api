package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.IngresoFablabItemDTO;
import com.example.demo.services.RegistroIngresoService;

@RestController
@RequestMapping("/ingresos-fablab")
public class RegistroIngresoController {
    
    @Autowired
    private RegistroIngresoService registroIngresoService;


    @GetMapping("/")
    public ResponseEntity<List<IngresoFablabItemDTO>> listar(){
        return ResponseEntity.ok().body(registroIngresoService.listar());
    }
}
