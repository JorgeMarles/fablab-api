package com.example.demo.services;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.MunicipioDTO;
import com.example.demo.entities.Municipio;
import com.example.demo.repositories.MunicipioRepository;

@Service
public class MunicipioService {
	@Autowired
	private MunicipioRepository municipioRepository;

	@Transactional
	public MunicipioDTO crear(Map<String, Object> municipioDTO) {
		Municipio municipio = new Municipio();

		if (!municipioDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(municipioDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}
		
		if (!municipioDTO.containsKey("codigo")) {
			throw new IllegalArgumentException("codigo es obligatorio.");
		}

		municipio.setNombre((String) municipioDTO.get("nombre"));
		municipio = municipioRepository.save(municipio);
		MunicipioDTO municipioResponse = new MunicipioDTO();
		municipioResponse.parseFromEntity(municipio);
		return municipioResponse;
	}

	public MunicipioDTO buscarPorId(Long id) {
		MunicipioDTO municipioResponse = new MunicipioDTO();
		municipioResponse.parseFromEntity(municipioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado.")));
		return municipioResponse;
	}

	public List<MunicipioDTO> listar() {
		List<MunicipioDTO> municipiosResponse = municipioRepository.findAll().stream().map(municipio -> {
			MunicipioDTO municipioResponse = new MunicipioDTO();
			municipioResponse.parseFromEntity(municipio);
			return municipioResponse;
		}).toList();
		return municipiosResponse;
	}

	public boolean existe(Long id) {
		return municipioRepository.existsById(id);
	}

	public Municipio buscarPorIdEntidad(Long id) {
		return municipioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado."));
	}

	public List<Municipio> listarEntidad() {
		return municipioRepository.findAll();
	}
}
