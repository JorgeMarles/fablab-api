package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.request.PlantillaCreacionDTO;
import com.example.demo.DTO.response.CertificadoItemDTO;
import com.example.demo.DTO.response.PlantillaDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.FileException;
import com.example.demo.services.CertificadoService;
import com.example.demo.services.FileService;
import com.example.demo.services.PlantillaService;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {
    @Autowired
    private CertificadoService certificadoService;

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private FileService fileService;

    @PostMapping("/plantillas/")
    public ResponseEntity<PlantillaDTO> crearCertificado(@ModelAttribute PlantillaCreacionDTO dto,
            @RequestParam("file") MultipartFile file) throws FileException, IOException {
    

        dto.setArchivo(file);


        PlantillaDTO plantilla = new PlantillaDTO();
        plantilla.parseFromEntity(plantillaService.crear(dto));
        
        return ResponseEntity.created(null).body(plantilla);
    }

    @GetMapping("/plantillas/")
    public ResponseEntity<List<PlantillaDTO>> obtenerPlantillas() {
        return ResponseEntity.ok().body(plantillaService.listar());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Resource> obtenerCertificado(@PathVariable Long id) throws FileException, IOException {
        Resource r = certificadoService.generarCertificado(id);
        String contentType = fileService.determineContentType("Certificado.doc");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"Certificado.doc\"")
                .header(HttpHeaders.CACHE_CONTROL, "max-age=1")
                .body(r);
    }

    @GetMapping("/")
    public ResponseEntity<List<CertificadoItemDTO>> listarCertificados() {
        Long id = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<CertificadoItemDTO> certs = certificadoService.listar(id);
        return ResponseEntity.ok(certs);
    }

}
