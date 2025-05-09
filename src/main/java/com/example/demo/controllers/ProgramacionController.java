package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/programacion")
public class ProgramacionController {
/*
    @Autowired
    private ProgramacionService programacionService;

	@GetMapping
	public ResponseEntity<List<ProgramacionDTO>> listarProgramaciones(@RequestParam(required = false) Long tallerId,
			@RequestParam(required = false) Boolean proximas) {
		if (tallerId != null) {
			List<Programacion> programaciones = programacionService.listarProgramacionesPorTaller(tallerId);
			return ResponseEntity.ok(ProgramacionDTO.fromEntity(programaciones));
		} else if (proximas != null) {
			List<Programacion> programaciones = programacionService.listarProgramacionesPosteriores();
			return ResponseEntity.ok(ProgramacionDTO.fromEntity(programaciones));
		} else {
			List<Programacion> programaciones = programacionService.listarProgramaciones();
			return ResponseEntity.ok(ProgramacionDTO.fromEntity(programaciones));
		}
	}

    @GetMapping("/{id}")
    public ResponseEntity<ProgramacionDTO> obtenerProgramacion(@PathVariable Long id) throws ResourceNotFoundException {
        Programacion programacion = programacionService.buscarProgramacion(id);
        return ResponseEntity.ok(ProgramacionDTO.fromEntity(programacion));
    }

    @PostMapping
    public ResponseEntity<?> crearProgramacion(@RequestBody ProgramacionRequestDTO programacionRequestDTO) {
        Programacion programacion = programacionRequestDTO.toEntity();
        programacion = programacionService.crearProgramacion(programacion);
        return ResponseEntity.status(201).body(ProgramacionDTO.fromEntity(programacion));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProgramacionDTO> actualizarProgramacion(@PathVariable Long id, @RequestBody ProgramacionRequestDTO programacionRequestDTO) throws ResourceNotFoundException {
        Programacion programacion = programacionRequestDTO.toEntity();
        programacion.setId(id);
        programacion = programacionService.actualizarProgramacion(programacion);
        return ResponseEntity.ok(ProgramacionDTO.fromEntity(programacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProgramacionDTO> eliminarProgramacion(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Programacion programacion = programacionService.eliminarProgramacion(id);
        return ResponseEntity.ok(ProgramacionDTO.fromEntity(programacion));
    }
    
*/    
}
