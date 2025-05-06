package com.example.demo.services;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.DTO.request.RegistroIngresoDTO;
import com.example.demo.DTO.response.IngresoFablabItemDTO;
import com.example.demo.entities.Motivo;
import com.example.demo.entities.RegistroIngreso;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.RegistroIngresoRepository;

@Service
public class RegistroIngresoService {
	@Autowired
	private RegistroIngresoRepository registroIngresoRepository;

	@Transactional
	public IngresoFablabItemDTO crear(RegistroIngresoDTO registroIngresoDTO, Usuario usuario) {
		RegistroIngreso registroIngreso = new RegistroIngreso();
		
		if (registroIngresoDTO.getMotivo() != null) {
			registroIngreso.setMotivo(registroIngresoDTO.getMotivo());
		} else {
			throw new IllegalArgumentException("El motivo es obligatorio.");
		}
		
		if(registroIngresoDTO.getMotivo()== Motivo.CURSO) {
				//ofertaFormacionService.buscarPorIdEntidad(registroIngresoDTO.getId_oferta_formacion());
		}
		
		
		return null;
	}
}
