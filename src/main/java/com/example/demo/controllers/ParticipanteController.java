package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participante")
public class ParticipanteController {
/*
    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listarParticipantes() {
        List<Participante> participantes = participanteService.listarParticipantes();
        return ResponseEntity.ok(ParticipanteDTO.fromEntity(participantes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> obtenerParticipante(@PathVariable Long id) throws ResourceNotFoundException {
        Participante participante = participanteService.buscarParticipante(id);
        return ResponseEntity.ok(ParticipanteDTO.fromEntity(participante));
    }

    @PostMapping
    public ResponseEntity<ParticipanteDTO> crearParticipante(@RequestBody ParticipanteRequestDTO participanteRequestDTO) throws ResourceNotFoundException {
        Participante participante = participanteRequestDTO.toEntity();
        participante = participanteService.crearParticipante(participante);
        return ResponseEntity.status(201).body(ParticipanteDTO.fromEntity(participante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteRequestDTO participanteRequestDTO) throws ResourceNotFoundException {
        Participante participante = participanteRequestDTO.toEntity();
        participante.setId(id);
        participante = participanteService.actualizarParticipante(participante);
        return ResponseEntity.ok(ParticipanteDTO.fromEntity(participante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> eliminarParticipante(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Participante participante = participanteService.eliminarParticipante(id);
        return ResponseEntity.ok(ParticipanteDTO.fromEntity(participante));
    }
*/}