package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.response.CategoriaDTO;
import com.example.demo.DTO.response.TipoBeneficiarioDTO;
import com.example.demo.DTO.response.TipoOfertaDTO;
import com.example.demo.services.CategoriaOfertaService;
import com.example.demo.services.TipoBeneficiarioService;
import com.example.demo.services.TipoOfertaService;

@RestController
@RequestMapping("/ofertas")
public class OfertaFormacionController {

    @Autowired
    private CategoriaOfertaService categoriaOfertaService;

    @Autowired
    private TipoOfertaService tipoOfertaService;

    @Autowired
    private TipoBeneficiarioService tipoBeneficiarioService;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(){
        return ResponseEntity.ok().body(categoriaOfertaService.listar());
    }

    @GetMapping("/tipos-oferta")
    public ResponseEntity<List<TipoOfertaDTO>> listarTiposOferta(){
        return ResponseEntity.ok().body(tipoOfertaService.listar());
    }

    @GetMapping("/tipos-beneficiario")
    public ResponseEntity<List<TipoBeneficiarioDTO>> listarTiposBeneficiario(){
        return ResponseEntity.ok().body(tipoBeneficiarioService.listar());
    }
}
