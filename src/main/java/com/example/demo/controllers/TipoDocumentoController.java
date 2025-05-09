package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.TipoDocumentoDTO;
import com.example.demo.services.TipoDocumentoService;

@RestController
@RequestMapping("/tipos-documento")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping("/")
    public ResponseEntity<List<TipoDocumentoDTO>> listar(){
        return ResponseEntity.ok().body(tipoDocumentoService.listar());
    }
}
