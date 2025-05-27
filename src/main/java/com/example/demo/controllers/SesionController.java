package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.request.EvidenciaCreacionDTO;
import com.example.demo.DTO.response.AsistenciaDTO;
import com.example.demo.DTO.response.AsistenciaTokenDTO;
import com.example.demo.DTO.response.SesionDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.FileException;
import com.example.demo.services.AsistenciaService;
import com.example.demo.services.EvidenciaService;
import com.example.demo.services.SesionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/sesiones")
public class SesionController {
    
    @Autowired
    private SesionService sesionService;

    @Autowired
    private EvidenciaService evidenciaService;

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/{id}/")
    public ResponseEntity<SesionDTO> obtenerPorId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok().body(sesionService.obtenerPorIdDto(id));
    }

    @PostMapping("/{id}/evidencias/")
    //Preauthorize admin or instructor (?) o solo instructor
    public ResponseEntity<?> subirEvidencia(@PathVariable(name = "id") Long id, @ModelAttribute EvidenciaCreacionDTO evidenciaDTO, @RequestParam("file") MultipartFile file) throws FileException, IOException {
        Long idInstructor = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        evidenciaDTO.setArchivo(file);
        evidenciaDTO.setId_sesion(id);
        evidenciaDTO.setId_instructor(idInstructor);
        return ResponseEntity.ok().body(evidenciaService.crear(evidenciaDTO));
    }

    @DeleteMapping("/{id}/evidencias/{evidenciaId}/")
    //Preauthorize admin
    public ResponseEntity<?> eliminarEvidencia(@PathVariable(name = "id") Long id, @PathVariable(name = "evidenciaId") Long evidenciaId) throws IOException {
        evidenciaService.eliminar(evidenciaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/asistencias/toggle-token/")
    public ResponseEntity<?> toggleAsistencia(@PathVariable(name = "id") Long id) {
        asistenciaService.toggleSesion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/asistencias/token/")
    public ResponseEntity<AsistenciaTokenDTO> getToken(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(asistenciaService.getToken(id));
    }

    @GetMapping("/{id}/asistencias/")
    public ResponseEntity<List<AsistenciaDTO>> obtenerAsistencias(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(asistenciaService.obtenerAsistenciasPorSesion(id));
    }

    @PostMapping("/{id}/asistencias/{idParticipante}/")
    public ResponseEntity<?> registrarAsistencia(@PathVariable(name = "id") Long id, @PathVariable(name = "idParticipante") Long idParticipante) {
        asistenciaService.toggleAsistencia(id, idParticipante);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/asistencias/")
    //Preauthorize participante
    public ResponseEntity<?> registrarAsistencia(@PathVariable(name = "id") Long id, @RequestBody(required = true) Map<String, String> body) {
        if (!body.containsKey("token")) {
            return ResponseEntity.badRequest().body("Token de asistencia requerido.");
        }
        Long idParticipante = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        asistenciaService.marcarAsistencia(id, idParticipante, body.get("token"));
        return ResponseEntity.ok().build();
    }
}
