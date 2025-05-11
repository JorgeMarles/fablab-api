package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.PlantillaCertificadoRepository;

@Service
public class PlantillaService {
    @Autowired
    private PlantillaCertificadoRepository plantillaCertificadoRepository;

    public PlantillaCertificado obtenerPorId(Long id) {
        return buscarPorIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada."));
    }

    public Optional<PlantillaCertificado> buscarPorIdOptional(Long id) {
        return plantillaCertificadoRepository.findById(id);
    }
}
