package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipo-documento")
public class TipoDocumentoController {
/*
    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping
    public ResponseEntity<List<TipoDocumentoDTO>> listarTiposDocumento() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.listarTipoDocumentos();
        return ResponseEntity.ok(TipoDocumentoDTO.fromEntity(tiposDocumento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> obtenerTipoDocumento(@PathVariable Long id) throws ResourceNotFoundException {
        TipoDocumento tipoDocumento = tipoDocumentoService.buscarTipoDocumento(id);
        return ResponseEntity.ok(TipoDocumentoDTO.fromEntity(tipoDocumento));
    }

    @PostMapping
    public ResponseEntity<TipoDocumentoDTO> crearTipoDocumento(@RequestBody TipoDocumentoRequestDTO tipoDocumentoRequestDTO) throws ResourceNotFoundException {
        TipoDocumento tipoDocumento = tipoDocumentoRequestDTO.toEntity();
        tipoDocumento = tipoDocumentoService.crearTipoDocumento(tipoDocumento);
        return ResponseEntity.status(201).body(TipoDocumentoDTO.fromEntity(tipoDocumento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> actualizarTipoDocumento(@PathVariable Long id, @RequestBody TipoDocumentoRequestDTO tipoDocumentoRequestDTO) throws ResourceNotFoundException {
        TipoDocumento tipoDocumento = tipoDocumentoRequestDTO.toEntity();
        tipoDocumento.setId(id);
        tipoDocumento = tipoDocumentoService.actualizarTipoDocumento(tipoDocumento);
        return ResponseEntity.ok(TipoDocumentoDTO.fromEntity(tipoDocumento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> eliminarTipoDocumento(@PathVariable Long id) throws ResourceNotFoundException, ResourceReferencedByOthersException {
        TipoDocumento tipoDocumento = tipoDocumentoService.eliminarTipoDocumento(id);
        return ResponseEntity.ok(TipoDocumentoDTO.fromEntity(tipoDocumento));
    }*/
}
