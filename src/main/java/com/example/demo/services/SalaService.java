package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.SalaDTO;
import com.example.demo.entities.Sala;
import com.example.demo.repositories.SalaRepository;

@Service
public class SalaService {
	@Autowired
	private SalaRepository salaRepository;
	
	@Transactional
	public SalaDTO crear(Map<String, Object> salaDTO) {
		Sala sala = new Sala();

		if (!salaDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(salaDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}

		sala.setNombre((String) salaDTO.get("nombre"));
		sala = salaRepository.save(sala);

		SalaDTO salaResponse = new SalaDTO();
		salaResponse.parseFromEntity(sala);

		return salaResponse;
	}

	public SalaDTO obtenerPorId(Long id) {
		SalaDTO salaResponse = new SalaDTO();
		salaResponse.parseFromEntity(
				salaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada.")));
		return salaResponse;
	}
	
	public List<SalaDTO> listar() {
		List<SalaDTO> salasResponse = salaRepository.findAll().stream().map(sala -> {
			SalaDTO salaResponse = new SalaDTO();
			salaResponse.parseFromEntity(sala);
			return salaResponse;
		}).toList();
		return salasResponse;
	}
	
	public boolean existe(Long id) {
		return salaRepository.existsById(id);
	}
	
	public  Optional<Sala> obtenerPorIdEntidad(Long id) {
		return salaRepository.findById(id);
	}
	
	public List<Sala> listarEntidad() {
		return salaRepository.findAll();
	}
}
