package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.PlantillaCreacionDTO;
import com.example.demo.DTO.response.PlantillaDTO;
import com.example.demo.entities.Archivo;
import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.PlantillaCertificadoRepository;

import jakarta.transaction.Transactional;

@Service
public class PlantillaService {
    @Autowired
    private PlantillaCertificadoRepository plantillaCertificadoRepository;

    @Autowired
    private FileService fileService;

    public PlantillaCertificado obtenerPorId(Long id) {
        return buscarPorIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada."));
    }

    public Optional<PlantillaCertificado> buscarPorIdOptional(Long id) {
        return plantillaCertificadoRepository.findById(id);
    }

    public List<PlantillaDTO> listar() {
        return plantillaCertificadoRepository.findAll().stream()
                .map(plantilla -> {
                    PlantillaDTO dto = new PlantillaDTO();
                    dto.parseFromEntity(plantilla);
                    return dto;
                }).toList();
    }

    @Transactional
    public PlantillaCertificado crear(PlantillaCreacionDTO dto) throws FileException, IOException {
        PlantillaCertificado plantilla = new PlantillaCertificado();
        plantilla.setNombre(dto.getNombre());

        Archivo archivo = fileService.uploadFile(dto.getArchivo());

        plantilla.setArchivo(archivo);
        return plantillaCertificadoRepository.save(plantilla);
    }
}
