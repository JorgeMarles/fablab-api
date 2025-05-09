package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
/*
    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> listarInstructores() {
        List<Instructor> instructores = instructorService.listarInstructores();
        return ResponseEntity.ok(InstructorDTO.fromEntity(instructores));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> obtenerInstructor(@PathVariable Long id) throws ResourceNotFoundException {
        Instructor instructor = instructorService.buscarInstructor(id);
        return ResponseEntity.ok(InstructorDTO.fromEntity(instructor));
    }

    @PostMapping
    public ResponseEntity<InstructorDTO> crearInstructor(@RequestBody InstructorRequestDTO instructorRequestDTO) throws ResourceNotFoundException {
        Instructor instructor = instructorRequestDTO.toEntity();
        instructor = instructorService.crearInstructor(instructor);
        return ResponseEntity.status(201).body(InstructorDTO.fromEntity(instructor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> actualizarInstructor(@PathVariable Long id, @RequestBody InstructorRequestDTO instructorRequestDTO) throws ResourceNotFoundException {
        Instructor instructor = instructorRequestDTO.toEntity();
        instructor.setId(id);
        instructor = instructorService.actualizarInstructor(instructor);
        return ResponseEntity.ok(InstructorDTO.fromEntity(instructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InstructorDTO> eliminarInstructor(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Instructor instructor = instructorService.eliminarInstructor(id);
        return ResponseEntity.ok(InstructorDTO.fromEntity(instructor));
    }
*/
}
