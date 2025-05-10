package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.InstructorDetalleDTO;
import com.example.demo.DTO.response.InstructorItemDTO;
import com.example.demo.services.InstructorService;

@RestController
@RequestMapping("/instructores")
public class InstructorController {
    
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/")
    private ResponseEntity<List<InstructorItemDTO>> listar(){
        return ResponseEntity.ok().body(instructorService.listar());
    }

    @GetMapping("/{id}/")
    private ResponseEntity<InstructorDetalleDTO> buscarPorId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(instructorService.buscarPorId(id));
    }
}
