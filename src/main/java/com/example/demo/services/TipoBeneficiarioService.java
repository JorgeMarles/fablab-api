package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.TipoBeneficiarioDTO;
import com.example.demo.entities.TipoBeneficiario;
import com.example.demo.repositories.TipoBeneficiarioRepository;

@Service
public class TipoBeneficiarioService {
	@Autowired
	private TipoBeneficiarioRepository tipoBeneficiarioRepository;

	@Transactional
	public TipoBeneficiarioDTO crear(Map<String, Object> tipoBeneficiarioDTO) {
		TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();

		if (!tipoBeneficiarioDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(tipoBeneficiarioDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}
		
		tipoBeneficiario.setNombre((String) tipoBeneficiarioDTO.get("nombre"));
		tipoBeneficiario = tipoBeneficiarioRepository.save(tipoBeneficiario);
		TipoBeneficiarioDTO tipoBeneficiarioResponse = new TipoBeneficiarioDTO();
		tipoBeneficiarioResponse.parseFromEntity(tipoBeneficiario);
		return tipoBeneficiarioResponse;
	}
	
	public TipoBeneficiarioDTO obtenerPorId(Long id) {
		TipoBeneficiarioDTO tipoBeneficiarioResponse = new TipoBeneficiarioDTO();
		tipoBeneficiarioResponse.parseFromEntity(tipoBeneficiarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de beneficiario no encontrado.")));
		return tipoBeneficiarioResponse;
	}
	
	public List<TipoBeneficiarioDTO> listar() {
		List<TipoBeneficiarioDTO> tiposBeneficiariosResponse = tipoBeneficiarioRepository.findAll().stream()
				.map(tipoBenef -> {
					TipoBeneficiarioDTO tipoBenefResponse = new TipoBeneficiarioDTO();
					tipoBenefResponse.parseFromEntity(tipoBenef);
					return tipoBenefResponse;
				}).toList();
		return tiposBeneficiariosResponse;
	}
	
	public boolean existe(Long id) {
		return tipoBeneficiarioRepository.existsById(id);
	}
	
	public  Optional<TipoBeneficiario> obtenerPorIdEntidad(Long id) {
		return tipoBeneficiarioRepository.findById(id);
	}
	
	public List<TipoBeneficiario> listarEntidad() {
		return tipoBeneficiarioRepository.findAll();
	}

}
