package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.MovimientoCreacionDTO;
import com.example.demo.DTO.response.MovimientoDTO;
import com.example.demo.services.MovimientoService;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/")
    public void guardarMovimiento(@RequestBody MovimientoCreacionDTO dto) {
        movimientoService.guardarMovimiento(dto);
    }

    @GetMapping("/")
    public ResponseEntity<List<MovimientoDTO>> listar() {
        return ResponseEntity.ok().body(movimientoService.listar());
    }

    @DeleteMapping("/{id}")
    public void eliminarMovimiento(@PathVariable(name = "id", required = true) Long id) {
        movimientoService.eliminarMovimiento(id);
    }
}
