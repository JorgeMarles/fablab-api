package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripcion")
public class InscripcionController {
/*
    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> listarInscripciones(@RequestParam(required = false) Long programacionId) {
    	 if (programacionId!=null) {
    		 List<Inscripcion> inscripciones = inscripcionService.listarInscripcionesPorProgramacion(programacionId);
    	        return ResponseEntity.ok(InscripcionDTO.fromEntity(inscripciones));
    	 }else {
    		 List<Inscripcion> inscripciones = inscripcionService.listarInscripciones();
    	        return ResponseEntity.ok(InscripcionDTO.fromEntity(inscripciones));
    	 }
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcion(@PathVariable Long id) throws ResourceNotFoundException {
        Inscripcion inscripcion = inscripcionService.buscarInscripcion(id);
        return ResponseEntity.ok(InscripcionDTO.fromEntity(inscripcion));
    }

    @PostMapping
    public ResponseEntity<InscripcionDTO> crearInscripcion(@RequestBody InscripcionRequestDTO inscripcionRequestDTO) throws ResourceNotFoundException {
        Inscripcion inscripcion = inscripcionRequestDTO.toEntity();
        inscripcion = inscripcionService.crearInscripcion(inscripcion);
        return ResponseEntity.status(201).body(InscripcionDTO.fromEntity(inscripcion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> actualizarInscripcion(@PathVariable Long id, @RequestBody InscripcionRequestDTO inscripcionRequestDTO) throws ResourceNotFoundException {
        Inscripcion inscripcion = inscripcionRequestDTO.toEntity();
        inscripcion.setId(id);
        inscripcion = inscripcionService.actualizarInscripcion(inscripcion);
        return ResponseEntity.ok(InscripcionDTO.fromEntity(inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InscripcionDTO> eliminarInscripcion(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Inscripcion inscripcion = inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.ok(InscripcionDTO.fromEntity(inscripcion));
    }
*/}
