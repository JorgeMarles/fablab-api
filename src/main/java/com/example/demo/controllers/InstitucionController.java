package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.InstitucionDTO;
import com.example.demo.DTO.response.TipoInstitucionDTO;
import com.example.demo.services.InstitucionService;
import com.example.demo.services.TipoInstitucionService;

@RestController
@RequestMapping("/instituciones")
public class InstitucionController {

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private TipoInstitucionService tipoInstitucionService;

    @GetMapping("/")
    public ResponseEntity<List<InstitucionDTO>> listarInstituciones(@RequestParam(required = false) Long tipoId) {
        if (tipoId == null)
            return ResponseEntity.ok().body(institucionService.listar());
        else
            return ResponseEntity.ok().body(institucionService.listarPorTipoId(tipoId));
    }

    @GetMapping("/tipos-institucion")
    public ResponseEntity<List<TipoInstitucionDTO>> listarTipos() {
        return ResponseEntity.ok().body(tipoInstitucionService.listar());
    }

}
