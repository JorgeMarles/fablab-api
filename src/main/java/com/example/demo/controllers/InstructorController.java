package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.DTO.response.InstructorItemDTO;
import com.example.demo.services.InstructorService;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/instructores")
public class InstructorController {
    
    @Autowired
    private InstructorService instructorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    private ResponseEntity<List<InstructorItemDTO>> listar(){
        return ResponseEntity.ok().body(instructorService.listar());
    }

    @PostMapping("/")
    private ResponseEntity<DatosPersonalesResponseDTO> crear(@RequestBody DatosPersonalesDTO datosPersonales) throws Exception{
        return ResponseEntity.ok().body(usuarioService.crearInstructor(datosPersonales));
    }

    @PutMapping("/{id}/")
    private ResponseEntity<DatosPersonalesResponseDTO> actualizar(@PathVariable(name = "id") Long id, @RequestBody DatosPersonalesDTO datosPersonales) throws Exception{
        return ResponseEntity.ok().body(usuarioService.actualizar(id, datosPersonales));
    }

    @PutMapping("/{id}/deshabilitar/")
    private ResponseEntity<String> switchEstado(@PathVariable(name = "id") Long id) throws Exception{
        instructorService.switchEstado(id);
        return ResponseEntity.ok().body("Se ha cambiado el estado del instructor correctamente");
    }

    @GetMapping("/{id}/")
    private ResponseEntity<DatosPersonalesResponseDTO> obtenerPorId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(instructorService.obtenerPorId(id));
    }
}
