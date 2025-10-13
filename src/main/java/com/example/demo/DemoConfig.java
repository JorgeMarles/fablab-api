package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.services.FirebaseService;
import com.example.demo.services.UsuarioService;

import jakarta.annotation.PreDestroy;

@Component
public class DemoConfig {

    @Value("${app.demo.enable}")
    private Boolean enable;

    @Value("${app.demo.email}")
    private String email;

    @Value("${app.demo.password}")
    private String password;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FirebaseService firebaseService;

    private boolean isDemoEnabled() {
        if (!enable)
            return false;

        if (email == null || email.length() == 0)
            return false;

        if (password == null || password.length() == 0)
            return false;

        return true;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDemoAccount() {
        if (!isDemoEnabled())
            return;

        DatosPersonalesDTO dto = new DatosPersonalesDTO();
        dto.setCorreo_personal(email);
        dto.setPassword(password);
        dto.setPrimer_nombre("Admin");

        usuarioService.crearAdministrador(dto);
    }

    @PreDestroy
    public void emptyFirebase() {
        if (!isDemoEnabled())
            return;

        firebaseService.deleteAllUsers();
    }
}
