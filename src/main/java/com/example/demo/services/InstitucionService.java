package com.example.demo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.InstitucionDTO;
import com.example.demo.entities.Institucion;
import com.example.demo.entities.TipoInstitucion;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.InstitucionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstitucionService {

	@Autowired
	private InstitucionRepository institucionRepository;

	@Transactional
	public InstitucionDTO crear(Map<String, Object> institucionDTO) {
		Institucion institucion = new Institucion();

		if (!institucionDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}

		if (!institucionDTO.containsKey("id_tipo_institucion")) {
			throw new IllegalArgumentException("Tipo de Institucion es obligatorio.");
		}

		if (!(institucionDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}

		TipoInstitucion tipoInstitucion = TipoInstitucion.valueOf(institucionDTO.get("tipo_institucion").toString());
		institucion.setNombre((String) institucionDTO.get("nombre"));
		institucion.setTipoInstitucion(tipoInstitucion);
		institucion = institucionRepository.save(institucion);

		InstitucionDTO institucionResponse = new InstitucionDTO();
		institucionResponse.parseFromEntity(institucion);

		return institucionResponse;
	}

	public InstitucionDTO obtenerPorId(Long id) {
		InstitucionDTO institucionResponse = new InstitucionDTO();
		institucionResponse.parseFromEntity(institucionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Institución no encontrada.")));
		return institucionResponse;//
	}

	public List<InstitucionDTO> listar() {
		List<InstitucionDTO> institucionesResponse = institucionRepository.findAll().stream().map(institucion -> {
			InstitucionDTO institucionResponse = new InstitucionDTO();
			institucionResponse.parseFromEntity(institucion);
			return institucionResponse;
		}).toList();
		return institucionesResponse;
	}

	public List<InstitucionDTO> listarPorTipo(TipoInstitucion tipoInstitucion) {
		return this.listarPorTipoEntidad(tipoInstitucion).stream().map(institucion -> {
			InstitucionDTO institucionResponse = new InstitucionDTO();
			institucionResponse.parseFromEntity(institucion);
			return institucionResponse;
		}).toList();
	}

	public List<Institucion> listarPorTipoEntidad(TipoInstitucion tipoInstitucion) {
		return institucionRepository.findByTipoInstitucion(tipoInstitucion);
	}

	public boolean existe(Long id) {
		return institucionRepository.existsById(id);
	}

	public Optional<Institucion> obtenerPorIdEntidad(Long id) {
		return institucionRepository.findById(id);
	}

	public List<Institucion> listarEntidad() {
		return institucionRepository.findAll();
	}
}
