package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.InstitucionDTO;
import com.example.demo.entities.TipoInstitucion;
import com.example.demo.services.InstitucionService;

@RestController
@RequestMapping("/instituciones")
public class InstitucionController {

    @Autowired
    private InstitucionService institucionService;


    @GetMapping("/")
    public ResponseEntity<List<InstitucionDTO>> listarInstituciones(@RequestParam(required = false) TipoInstitucion tipo) {
        if (tipo == null)
            return ResponseEntity.ok().body(institucionService.listar());
        else
            return ResponseEntity.ok().body(institucionService.listarPorTipo(tipo));
    }

    @GetMapping("/tipos-institucion")
    public ResponseEntity<List<TipoInstitucion>> listarTipos() {
        return ResponseEntity.ok().body(List.of(TipoInstitucion.values()));
    }

}
