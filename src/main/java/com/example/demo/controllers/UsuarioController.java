package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.DTO.response.UserInfoDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.services.FirebaseService;
import com.example.demo.services.ParticipanteService;
import com.example.demo.services.UsuarioService;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {
    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/roles/")
    public ResponseEntity<?> getRoles(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FirebaseToken token = (FirebaseToken) authentication.getCredentials();
        firebaseService.verificarDatos(token);
        UserInfoDTO dto = firebaseService.getUserData(token.getUid());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/datos-personales/")
    public ResponseEntity<DatosPersonalesResponseDTO> obtenerDatosPersonales() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(usuarioService.obtenerPorId(usuario.getId()));
    }

    @PostMapping("/datos-personales/")
    public ResponseEntity<?> registrarDatosPersonales(
            @RequestBody DatosPersonalesDTO datosPersonales) throws Exception {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return ResponseEntity.created(null).body(participanteService.crearParticipante(datosPersonales, usuario));
    }

    @PutMapping("/datos-personales/")
    public ResponseEntity<?> actualizarDatosPersonales(
            @RequestBody DatosPersonalesDTO datosPersonales) throws Exception {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.actualizar(usuario.getId(), datosPersonales));
    }

}
