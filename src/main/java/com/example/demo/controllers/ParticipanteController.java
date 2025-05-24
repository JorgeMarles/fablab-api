package com.example.demo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<DatosPersonalesResponseDTO> crearParticipante(@RequestBody DatosPersonalesDTO participanteDTO) {
        DatosPersonalesResponseDTO creado = usuarioService.crearParticipante(participanteDTO, null, true);
        return ResponseEntity.created(URI.create("/participantes/" + creado.getId())).body(creado);
    }
}
