package com.example.demo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.ModalidadDTO;
import com.example.demo.entities.Modalidad;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ModalidadRepository;

import jakarta.transaction.Transactional;

@Service
public class ModalidadService {
    
    @Autowired
    private ModalidadRepository modalidadRepository;

    @Transactional
    public ModalidadDTO crearModalidad(Map<String, Object> modalidadCreacionDTO) {
        Modalidad modalidad = new Modalidad();
        
        if (!modalidadCreacionDTO.containsKey("nombre")) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        
        modalidad.setNombre((String) modalidadCreacionDTO.get("nombre"));
        
        modalidad = modalidadRepository.save(modalidad);
        ModalidadDTO modalidadResponse = new ModalidadDTO();
        modalidadResponse.parseFromEntity(modalidad);
        return modalidadResponse;
    }

    public ModalidadDTO buscarPorId(Long id) {
		ModalidadDTO ModalidadResponse = new ModalidadDTO();
		ModalidadResponse.parseFromEntity(modalidadRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Modalidad no encontrado.")));
		return ModalidadResponse;
	}

	public List<ModalidadDTO> listar() {
		List<ModalidadDTO> ModalidadsResponse = modalidadRepository.findAll().stream().map(Modalidad -> {
			ModalidadDTO ModalidadResponse = new ModalidadDTO();
			ModalidadResponse.parseFromEntity(Modalidad);
			return ModalidadResponse;
		}).toList();
		return ModalidadsResponse;
	}

	public boolean existe(Long id) {
		return modalidadRepository.existsById(id);
	}

	public Optional<Modalidad> buscarPorIdEntidad(Long id) {
		return modalidadRepository.findById(id);
	}

	public List<Modalidad> listarEntidad() {
		return modalidadRepository.findAll();
	}
}
