package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/municipio")
public class MunicipioController {
/*
	@Autowired
    private MunicipioService municipioService;

    @GetMapping
    public ResponseEntity<List<MunicipioDTO>> listarMunicipios() {
        List<Municipio> municipios = municipioService.listarMunicipios();
        return ResponseEntity.ok(MunicipioDTO.fromEntity(municipios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipioDTO> obtenerMunicipio(@PathVariable Long id) throws ResourceNotFoundException {
        Municipio municipio = municipioService.buscarMunicipio(id);
        return ResponseEntity.ok(MunicipioDTO.fromEntity(municipio));
    }

    @PostMapping
    public ResponseEntity<MunicipioDTO> crearMunicipio(@RequestBody MunicipioRequestDTO municipioRequestDTO) {
        Municipio municipio = municipioRequestDTO.toEntity();
        municipio = municipioService.crearMunicipio(municipio);
        return ResponseEntity.status(201).body(MunicipioDTO.fromEntity(municipio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MunicipioDTO> actualizarMunicipio(@PathVariable Long id, @RequestBody MunicipioRequestDTO municipioRequestDTO) throws ResourceNotFoundException {
        Municipio municipio = municipioRequestDTO.toEntity();
        municipio.setId(id);
        municipio = municipioService.actualizarMunicipio(municipio);
        return ResponseEntity.ok(MunicipioDTO.fromEntity(municipio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MunicipioDTO> eliminarMunicipio(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        Municipio municipio = municipioService.eliminarMunicipio(id);
        return ResponseEntity.ok(MunicipioDTO.fromEntity(municipio));
    }
*/}