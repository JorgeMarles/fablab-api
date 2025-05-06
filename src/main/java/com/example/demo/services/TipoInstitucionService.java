package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.response.TipoInstitucionDTO;
import com.example.demo.entities.TipoInstitucion;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.TipoInstitucionRepository;

@Service
public class TipoInstitucionService {
	
	@Autowired
	private TipoInstitucionRepository tipoInstitucionRepository;
	
	@Transactional
    public TipoInstitucionDTO crear(Map<String, Object> tipoInstitucionDTO) {
       
        TipoInstitucion tipoInstitucion = new TipoInstitucion();
        
		if (!tipoInstitucionDTO.containsKey("nombre")) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		tipoInstitucion.setNombre((String) tipoInstitucionDTO.get("nombre"));
		
		tipoInstitucion = tipoInstitucionRepository.save(tipoInstitucion);
		TipoInstitucionDTO tipoInstitucionResponse = new TipoInstitucionDTO();
		tipoInstitucionResponse.parseFromEntity(tipoInstitucion);
		return tipoInstitucionResponse;
    }
    
	public TipoInstitucionDTO buscarPorId(Long id) {
		TipoInstitucionDTO tipoInstitucionResponse = new TipoInstitucionDTO();
        tipoInstitucionResponse.parseFromEntity(tipoInstitucionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de institucion no encontrado.")));
        return tipoInstitucionResponse;
    }
	
	public List<TipoInstitucionDTO> listar() {
		List<TipoInstitucionDTO> tipoInstitucionesResponse = new ArrayList<>();
        for (TipoInstitucion tipoInstitucion : tipoInstitucionRepository.findAll()) {
            TipoInstitucionDTO tipoInstitucionResponse = new TipoInstitucionDTO();
            tipoInstitucionResponse.parseFromEntity(tipoInstitucion);
            tipoInstitucionesResponse.add(tipoInstitucionResponse);
        }
        return tipoInstitucionesResponse;
    }
 }

