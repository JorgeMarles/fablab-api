
package com.example.demo.services;

import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.ProgramaAcademicoDTO;
import com.example.demo.entities.ProgramaAcademico;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ProgramaAcademicoRepository;

@Service
public class ProgramaAcademicoService {

    @Autowired
    private ProgramaAcademicoRepository programaAcademicoRepository;

    @Transactional
    public ProgramaAcademicoDTO crear(Map<String, Object> programaAcademicoDTO) {
        ProgramaAcademico programaAcademico = new ProgramaAcademico();

        if (!programaAcademicoDTO.containsKey("nombre")) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        programaAcademico.setNombre((String) programaAcademicoDTO.get("nombre"));
        programaAcademico = programaAcademicoRepository.save(programaAcademico);

        ProgramaAcademicoDTO programaAcademicoResponse = new ProgramaAcademicoDTO();
        programaAcademicoResponse.parseFromEntity(programaAcademico);

        return programaAcademicoResponse;
    }

	public ProgramaAcademicoDTO buscarPorNombre(String nombre) {
		ProgramaAcademico programaAcademico = programaAcademicoRepository.findByNombre(nombre)
				.orElseThrow(() -> new ResourceNotFoundException("Programa académico no encontrado."));
		ProgramaAcademicoDTO programaAcademicoResponse = new ProgramaAcademicoDTO();
		programaAcademicoResponse.parseFromEntity(programaAcademico);
		return programaAcademicoResponse;
	}
	
	public ProgramaAcademicoDTO buscarPorId(Long id) {
		ProgramaAcademicoDTO programaAcademicoResponse = new ProgramaAcademicoDTO();
		programaAcademicoResponse.parseFromEntity(programaAcademicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Programa académico no encontrado.")));
		return programaAcademicoResponse;
	}
	
	public ProgramaAcademicoDTO buscarPoCodigo(String codigo) {
		ProgramaAcademicoDTO programaAcademicoResponse = new ProgramaAcademicoDTO();
		programaAcademicoResponse.parseFromEntity(programaAcademicoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new ResourceNotFoundException("Programa académico no encontrado.")));
		return programaAcademicoResponse;
	}
	
	public boolean existe(Long id) {
		return programaAcademicoRepository.existsById(id);
	}
	
	public List<ProgramaAcademicoDTO> listar() {
		List<ProgramaAcademicoDTO> programasAcademicosResponse = programaAcademicoRepository.findAll().stream()
				.map(programaAcademico -> {
					ProgramaAcademicoDTO programaAcademicoResponse = new ProgramaAcademicoDTO();
					programaAcademicoResponse.parseFromEntity(programaAcademico);
					return programaAcademicoResponse;
				}).toList();
		return programasAcademicosResponse;
	}
	
	public ProgramaAcademico buscarPorIdEntidad(Long id) {
		return programaAcademicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Programa académico no encontrado."));
	}
	
	public List<ProgramaAcademico> listarEntidad() {
		return programaAcademicoRepository.findAll();
	}
}
