package com.example.demo.services;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;

import com.example.demo.DTO.response.SemilleroDTO;
import com.example.demo.entities.Semillero;
import com.example.demo.repositories.SemilleroRepository;

@Service
public class SemilleroService {
	@Autowired
	private SemilleroRepository semilleroRepository;

	@Transactional
    public SemilleroDTO crear(Map<String, Object> semilleroDTO) {
        Semillero semillero = new Semillero();

        if (!semilleroDTO.containsKey("nombre")) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        
		if (!(semilleroDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}
		
		if (!(semilleroDTO.get("siglas") instanceof String)) {
			throw new IllegalArgumentException("siglas debe ser un texto.");
		}
		
        semillero.setNombre((String) semilleroDTO.get("nombre"));
        semillero = semilleroRepository.save(semillero);

        SemilleroDTO semilleroResponse = new SemilleroDTO();
        semilleroResponse.parseFromEntity(semillero);

        return semilleroResponse;
	}
	
	public SemilleroDTO buscarPorId(Long id) {
		SemilleroDTO semilleroResponse = new SemilleroDTO();
		semilleroResponse.parseFromEntity(semilleroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Semillero no encontrado.")));
		return semilleroResponse;
	}
	
	public List<SemilleroDTO> listar() {
		List<SemilleroDTO> semillerosResponse = semilleroRepository.findAll().stream().map(semillero -> {
			SemilleroDTO semilleroResponse = new SemilleroDTO();
			semilleroResponse.parseFromEntity(semillero);
			return semilleroResponse;
		}).toList();
		return semillerosResponse;
	}

	public boolean existe(Long id) {
		return semilleroRepository.existsById(id);
	}
	
	public Semillero buscarPorIdEntidad(Long id) {
		return semilleroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Semillero no encontrado."));
	}
	
	public List<Semillero> listarEntidad() {
		return semilleroRepository.findAll();
	}
}
