package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.PoblacionEspecialDTO;
import com.example.demo.entities.PoblacionEspecial;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.PoblacionEspecialRepository;

import jakarta.transaction.Transactional;

@Service
public class PoblacionEspecialService {

	@Autowired
	private PoblacionEspecialRepository poblacionEspecialRepository;

	@Transactional
	public PoblacionEspecialDTO crear(Map<String, Object> poblacionEspecialDTO) {
		PoblacionEspecial poblacionEspecial = new PoblacionEspecial();

		if (!poblacionEspecialDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(poblacionEspecialDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}

		poblacionEspecial.setNombre((String) poblacionEspecialDTO.get("nombre"));
		poblacionEspecial = poblacionEspecialRepository.save(poblacionEspecial);
		PoblacionEspecialDTO poblacionEspecialResponse = new PoblacionEspecialDTO();
		poblacionEspecialResponse.parseFromEntity(poblacionEspecial);
		return poblacionEspecialResponse;
	}
	
	public PoblacionEspecialDTO buscarPorId(Long id) {
		PoblacionEspecialDTO poblacionEspecialResponse = new PoblacionEspecialDTO();
		poblacionEspecialResponse.parseFromEntity(poblacionEspecialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Poblacion especial no encontrada.")));
		return poblacionEspecialResponse;
	}
	
	public List<PoblacionEspecialDTO> listar() {
		List<PoblacionEspecialDTO> poblacionesEspecialesResponse = poblacionEspecialRepository.findAll().stream()
				.map(poblacionEspecial -> {
					PoblacionEspecialDTO poblacionEspecialResponse = new PoblacionEspecialDTO();
					poblacionEspecialResponse.parseFromEntity(poblacionEspecial);
					return poblacionEspecialResponse;
				}).toList();
		return poblacionesEspecialesResponse;
	}
	
	public boolean existe(Long id) {
		return poblacionEspecialRepository.existsById(id);
	}
	
	public Optional<PoblacionEspecial> buscarPorIdEntidad(Long id) {
		return poblacionEspecialRepository.findById(id);
	}

	public List<PoblacionEspecial> listarEntidad() {
		return poblacionEspecialRepository.findAll();
	}
}
