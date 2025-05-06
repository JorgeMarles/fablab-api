package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.PaisDTO;
import com.example.demo.entities.Pais;
import com.example.demo.repositories.PaisRepository;

@Service
public class PaisService {
	@Autowired
	private PaisRepository paisRepository;

	@Transactional
	public PaisDTO crear(Map<String, Object> paisDTO) {
		Pais pais = new Pais();

		if (!paisDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(paisDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}
		
		if (!paisDTO.containsKey("codigo")) {
			throw new IllegalArgumentException("codigo es obligatorio.");
		}

		pais.setNombre((String) paisDTO.get("nombre"));
		pais = paisRepository.save(pais);
		PaisDTO paisResponse = new PaisDTO();
		paisResponse.parseFromEntity(pais);
		return paisResponse;
	}

	public PaisDTO buscarPorId(Long id) {
		PaisDTO paisResponse = new PaisDTO();
		paisResponse.parseFromEntity(
				paisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pais no encontrado.")));
		return paisResponse;
	}

	public List<PaisDTO> listar() {
		List<PaisDTO> paisesResponse = paisRepository.findAll().stream().map(pais -> {
			PaisDTO paisResponse = new PaisDTO();
			paisResponse.parseFromEntity(pais);
			return paisResponse;
		}).toList();
		return paisesResponse;
	}

	public boolean existe(Long id) {
		return paisRepository.existsById(id);
	}

	public Optional<Pais> buscarPorIdEntidad(Long id) {
		return paisRepository.findById(id);
	}

	public List<Pais> listarEntidad() {
		return paisRepository.findAll();
	}
}
