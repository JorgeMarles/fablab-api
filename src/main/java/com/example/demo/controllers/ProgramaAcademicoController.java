package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.ProgramaAcademicoDTO;
import com.example.demo.services.ProgramaAcademicoService;

@RestController
@RequestMapping("/programas-academicos")
public class ProgramaAcademicoController {
    
    @Autowired
    private ProgramaAcademicoService programaAcademicoService;
    

    @GetMapping("/")
    public ResponseEntity<List<ProgramaAcademicoDTO>> listar(){
        return ResponseEntity.ok().body(programaAcademicoService.listar());
    }
}
