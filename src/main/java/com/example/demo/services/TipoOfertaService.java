package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.TipoOfertaDTO;
import com.example.demo.entities.TipoOferta;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.TipoOfertaRepository;

import jakarta.transaction.Transactional;

@Service
public class TipoOfertaService {

    @Autowired
    private TipoOfertaRepository tipoOfertaRepository;

    @Transactional
    public TipoOfertaDTO crear(Map<String, Object> tipoOfertaDTO) {

        TipoOferta tipoOferta = new TipoOferta();

        if (!tipoOfertaDTO.containsKey("nombre")) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        tipoOferta.setNombre((String) tipoOfertaDTO.get("nombre"));


        tipoOferta = tipoOfertaRepository.save(tipoOferta);
        TipoOfertaDTO tipoOfertaResponse = new TipoOfertaDTO();
        tipoOfertaResponse.parseFromEntity(tipoOferta);
        return tipoOfertaResponse;
    }

    public TipoOfertaDTO buscarPorId(Long id) {
        TipoOfertaDTO tipoOfertaResponse = new TipoOfertaDTO();
        tipoOfertaResponse.parseFromEntity(tipoOfertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de oferta no encontrado.")));
        return tipoOfertaResponse;
    }

    public List<TipoOfertaDTO> listar() {
        List<TipoOfertaDTO> tipoOfertasResponse = new ArrayList<>();
        for (TipoOferta tipoOferta : tipoOfertaRepository.findAll()) {
            TipoOfertaDTO tipoOfertaResponse = new TipoOfertaDTO();
            tipoOfertaResponse.parseFromEntity(tipoOferta);
            tipoOfertasResponse.add(tipoOfertaResponse);
        }
        return tipoOfertasResponse;
    }

    public boolean existe(Long id) {
        return tipoOfertaRepository.existsById(id);
    }
    
	public TipoOferta buscarPorIdEntidad(Long id) {
		return tipoOfertaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de oferta no encontrado."));
	}
	
	public List<TipoOferta> listarEntidad() {
		return tipoOfertaRepository.findAll();
	}
}
