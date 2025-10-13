package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.entities.Administrador;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.AdministradorRepository;

import jakarta.transaction.Transactional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Transactional
    public DatosPersonalesResponseDTO crearAdministrador(Usuario usuario) {
        Administrador administrador = new Administrador();
        administrador.setUsuario(usuario);

        administrador = administradorRepository.save(administrador);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(administrador.getUsuario());

        return idto;
    }

}
