package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.TipoDocumentoDTO;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.repositories.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Transactional
	public TipoDocumentoDTO crear(Map<String, Object> tipoDocumentoDTO) {
		TipoDocumento tipoDocumento = new TipoDocumento();

		if (!tipoDocumentoDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(tipoDocumentoDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}
		
		if (!(tipoDocumentoDTO.get("siglas") instanceof String)) {
			throw new IllegalArgumentException("siglas debe ser un texto.");
		}

		tipoDocumento.setNombre((String) tipoDocumentoDTO.get("nombre"));
		tipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
		TipoDocumentoDTO tipoDocumentoResponse = new TipoDocumentoDTO();
		tipoDocumentoResponse.parseFromEntity(tipoDocumento);
		return tipoDocumentoResponse;
	}

	public TipoDocumentoDTO buscarPorId(Long id) {
		TipoDocumentoDTO tipoDocumentoResponse = new TipoDocumentoDTO();
		tipoDocumentoResponse.parseFromEntity(tipoDocumentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de documento no encontrado.")));
		return tipoDocumentoResponse;
	}

	public List<TipoDocumentoDTO> listar() {
		List<TipoDocumentoDTO> tiposDocumentosResponse = tipoDocumentoRepository.findAll().stream().map(tipoDoc -> {
			TipoDocumentoDTO tipoDocResponse = new TipoDocumentoDTO();
			tipoDocResponse.parseFromEntity(tipoDoc);
			return tipoDocResponse;
		}).toList();
		return tiposDocumentosResponse;
	}

	public boolean existe(Long id) {
		return tipoDocumentoRepository.existsById(id);
	}

	public Optional<TipoDocumento> buscarPorIdEntidad(Long id) {
		return tipoDocumentoRepository.findById(id);
	}

	public List<TipoDocumento> listarEntidad() {
		return tipoDocumentoRepository.findAll();
	}
}
