package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}/")
    public ResponseEntity<DatosPersonalesResponseDTO> obtenerParticipantePorId(@PathVariable Long id) {
        DatosPersonalesResponseDTO dto = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<DatosPersonalesResponseDTO>> obtenerParticipantePorEmail(@RequestBody String q) {
        return ResponseEntity.ok(usuarioService.buscar(q));
    }
}
