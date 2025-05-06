package com.example.demo.services;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.response.CategoriaDTO;
import com.example.demo.entities.CategoriaOferta;
import com.example.demo.repositories.CategoriaOfertaRepository;

@Service
public class CategoriaOfertaService {
	@Autowired
	private CategoriaOfertaRepository categoriaOfertaRepository;

	@Transactional
	public CategoriaDTO crear(Map<String, Object> categoriaDTO) {
		CategoriaOferta categoriaOferta = new CategoriaOferta();

		if (!categoriaDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}

		categoriaOferta.setNombre((String) categoriaDTO.get("nombre"));
		categoriaOferta = categoriaOfertaRepository.save(categoriaOferta);
		CategoriaDTO categoriaResponse = new CategoriaDTO();
		categoriaResponse.parseFromEntity(categoriaOferta);
		return categoriaResponse;
	}
	
	public CategoriaDTO buscarPorId(Long id) {
		CategoriaDTO categoriaResponse = new CategoriaDTO();
		categoriaResponse.parseFromEntity(categoriaOfertaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada.")));
		return categoriaResponse;
	}
	
	public List<CategoriaDTO> listar() {
		List<CategoriaDTO> categoriasResponse = categoriaOfertaRepository.findAll().stream().map(categoria -> {
			CategoriaDTO categoriaResponse = new CategoriaDTO();
			categoriaResponse.parseFromEntity(categoria);
			return categoriaResponse;
		}).toList();
		return categoriasResponse;
	}

	public boolean existe(Long id) {
		return categoriaOfertaRepository.existsById(id);
	}
	
	public CategoriaOferta buscarPorIdEntidad(Long id) {
		return categoriaOfertaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada."));
	}
	
	public List<CategoriaOferta> listarEntidad() {
		return categoriaOfertaRepository.findAll();
	}

}
