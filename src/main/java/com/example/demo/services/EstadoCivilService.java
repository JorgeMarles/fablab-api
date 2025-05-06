package com.example.demo.services;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.EstadoCivilDTO;
import com.example.demo.entities.EstadoCivil;
import com.example.demo.repositories.EstadoCivilRepository;

@Service
public class EstadoCivilService {

	@Autowired
	private EstadoCivilRepository estadoCivilRepository;

	@Transactional
	public EstadoCivilDTO crear(Map<String, Object> estadoCivilDTO) {
		EstadoCivil estadoCivil = new EstadoCivil();

		if (!estadoCivilDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(estadoCivilDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}

		estadoCivil.setNombre((String) estadoCivilDTO.get("nombre"));
		estadoCivil = estadoCivilRepository.save(estadoCivil);
		EstadoCivilDTO estadoCivilResponse = new EstadoCivilDTO();
		estadoCivilResponse.parseFromEntity(estadoCivil);
		return estadoCivilResponse;
	}

	public EstadoCivilDTO buscarPorId(Long id) {
		EstadoCivilDTO estadoCivilResponse = new EstadoCivilDTO();
		estadoCivilResponse.parseFromEntity(estadoCivilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estado civil no encontrado.")));
		return estadoCivilResponse;
	}

	public List<EstadoCivilDTO> listar() {
		List<EstadoCivilDTO> estadosCivilesResponse = estadoCivilRepository.findAll().stream().map(estadoCivil -> {
			EstadoCivilDTO estadoCivilResponse = new EstadoCivilDTO();
			estadoCivilResponse.parseFromEntity(estadoCivil);
			return estadoCivilResponse;
		}).toList();
		return estadosCivilesResponse;
	}

	public boolean existe(Long id) {
		return estadoCivilRepository.existsById(id);
	}
	
	public List<EstadoCivil> listarEntidad() {
		return estadoCivilRepository.findAll();
	}
	
	public EstadoCivil buscarPorIdEntidad(Long id) {
		return estadoCivilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estado civil no encontrado."));
	}
}
