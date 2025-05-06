package com.example.demo.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.CargoDTO;
import com.example.demo.entities.Cargo;
import com.example.demo.repositories.CargoRepository;

@Service
public class CargoService {
	@Autowired
	private CargoRepository cargoRepository;

	@Transactional
	public CargoDTO crear(Map<String, Object> cargoDTO) {
		Cargo cargo = new Cargo();

		if (!cargoDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		
		if (!(cargoDTO.get("nombre") instanceof String)) {
			throw new IllegalArgumentException("El nombre debe ser un texto.");
		}

		cargo.setNombre((String) cargoDTO.get("nombre"));
		cargo = cargoRepository.save(cargo);

		CargoDTO cargoResponse = new CargoDTO();
		cargoResponse.parseFromEntity(cargo);

		return cargoResponse;
	}

	public CargoDTO buscarPorId(Long id) {
		CargoDTO cargoResponse = new CargoDTO();
		cargoResponse.parseFromEntity(
				cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo no encontrado.")));
		return cargoResponse;
	}

	public List<CargoDTO> listar() {
		List<CargoDTO> cargosResponse = cargoRepository.findAll().stream().map(cargo -> {
			CargoDTO cargoResponse = new CargoDTO();
			cargoResponse.parseFromEntity(cargo);
			return cargoResponse;
		}).toList();
		return cargosResponse;
	}

	public boolean existe(Long id) {
		return cargoRepository.existsById(id);
	}

	public  Optional<Cargo> buscarPorIdEntidad(Long id) {
		return cargoRepository.findById(id);
	}
	
	public List<Cargo> listarEntidad() {
		return cargoRepository.findAll();
	}
}
