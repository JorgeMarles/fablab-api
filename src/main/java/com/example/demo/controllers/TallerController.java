package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taller")
public class TallerController {
/*
    @Autowired
    private TallerService tallerService;

    @GetMapping
    public ResponseEntity<List<TallerDTO>> listarTalleres() {
        List<Taller> talleres = tallerService.listarTalleres();
        return ResponseEntity.ok(TallerDTO.fromEntity(talleres));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TallerDTO> obtenerTaller(@PathVariable Long id) throws ResourceNotFoundException {
        Taller taller = tallerService.buscarTaller(id);
        return ResponseEntity.ok(TallerDTO.fromEntity(taller));
    }

    @PostMapping
    public ResponseEntity<TallerDTO> crearTaller(@RequestBody TallerRequestDTO tallerRequestDTO) throws ResourceNotFoundException {
        Taller taller = tallerRequestDTO.toEntity();
        taller = tallerService.crearTaller(taller);
        return ResponseEntity.status(201).body(TallerDTO.fromEntity(taller));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TallerDTO> actualizarTaller(@PathVariable Long id, @RequestBody TallerRequestDTO tallerRequestDTO) throws ResourceNotFoundException {
        Taller taller = tallerRequestDTO.toEntity();
        taller.setId(id);
        taller = tallerService.actualizarTaller(taller);
        return ResponseEntity.ok(TallerDTO.fromEntity(taller));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TallerDTO> eliminarTaller(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Taller taller = tallerService.eliminarTaller(id);
        return ResponseEntity.ok(TallerDTO.fromEntity(taller));
    }*/
}
