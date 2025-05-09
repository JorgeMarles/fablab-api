package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.OfertaDetalleDTO;
import com.example.demo.DTO.response.OfertaItemDTO;
import com.example.demo.services.OfertaFormacionService;

@RestController
@RequestMapping("/ofertas")
public class OfertaFormacionController {
    
    @Autowired
    private OfertaFormacionService ofertaFormacionService;

    @GetMapping("/")
    //@Preauthorize admin
    public ResponseEntity<List<OfertaItemDTO>> listarTodos(){
        return ResponseEntity.ok().body(ofertaFormacionService.listarTodos());
    }

    @GetMapping("/instructor")
    //preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarInstructor(){
        //TODO: Sacar del userdetails del authorization
        Long id = 3L;
        return ResponseEntity.ok().body(ofertaFormacionService.listarInstructor(id)); 
    }

    @GetMapping("/participante")
    //preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarParticipante(){
        //TODO: Sacar del userdetails del authorization
        Long id = 3L;
        return ResponseEntity.ok().body(ofertaFormacionService.listarParticipante(id)); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaDetalleDTO> detalle(@PathVariable(name = "id") Long idOferta){
        return ResponseEntity.ok().body(ofertaFormacionService.obtenerPorIdDetalle(idOferta));
    }
}
