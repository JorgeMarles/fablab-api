package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.SesionDTO;
import com.example.demo.DTO.response.SesionItemDTO;
import com.example.demo.entities.Sesion;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.SesionRepository;

@Service
public class SesionService {
    
    @Autowired
    private SesionRepository sesionRepository;

    public List<Sesion> listar(){
        return sesionRepository.findAll();
    }

    public List<SesionItemDTO> listarDto(){
        return this.listar().stream().map(sesion -> {
            SesionItemDTO dto = new SesionItemDTO();
            dto.parseFromEntity(sesion);
            return dto;
        }).toList();
    }

    public List<Sesion> listarPorOfertaId(Long ofertaId) {
        return sesionRepository.findByOfertaFormacion_Id(ofertaId);
    }

    public List<SesionItemDTO> listarPorOfertaIdDto(Long ofertaId){
        return this.listarPorOfertaId(ofertaId).stream().map(sesion -> {
            SesionItemDTO dto = new SesionItemDTO();
            dto.parseFromEntity(sesion);
            return dto;
        }).toList();
    }

    public List<Sesion> listarPorOfertaIdEInstructorId(Long ofertaId, Long instructorId) {
        return sesionRepository.findByOfertaFormacion_IdAndInstructores_Id(ofertaId, instructorId);
    }

    public List<SesionItemDTO> listarPorOfertaIdEInstructorIdDto(Long ofertaId, Long instructorId){
        return this.listarPorOfertaIdEInstructorId(ofertaId, instructorId).stream().map(sesion -> {
            SesionItemDTO dto = new SesionItemDTO();
            dto.parseFromEntity(sesion);
            return dto;
        }).toList();
    }

    public Optional<Sesion> obtenerPorIdEntidad(Long id){
        return sesionRepository.findById(id);
    }

    public SesionDTO obtenerPorIdDto(Long id){
        Optional<Sesion> opt = this.obtenerPorIdEntidad(id);
        if(!opt.isPresent()){
            throw new ResourceNotFoundException("No existe una sesión con ese id");
        }
        
        SesionDTO dto = new SesionDTO();

        dto.parseFromEntity(opt.get());

        return dto;
    }

}
