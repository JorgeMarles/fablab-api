package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.SesionCreacionDTO;
import com.example.demo.DTO.response.SesionDTO;
import com.example.demo.DTO.response.SesionItemDTO;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.Sala;
import com.example.demo.entities.Sesion;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.SesionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SesionService {
    
    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private SalaService salaService;

    @Autowired
    private InstructorService instructorService;

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

    public void validar(SesionCreacionDTO sesionDto){
        if(!salaService.existe(sesionDto.getId_sala())){
            throw new ResourceNotFoundException("No existe una sala con ese id");
        }
        for(Long idInstructor : sesionDto.getInstructores()) {
            if(!instructorService.existe(idInstructor)){
                throw new ResourceNotFoundException("No existe un instructor con ese id");
            }
        }
    }

    @Transactional
    public Sesion crear(SesionCreacionDTO sesionDto, OfertaFormacion oferta, int order) {

        Sesion sesion = new Sesion();
        
        Optional<Sala> salaOpt = salaService.obtenerPorIdEntidad(sesionDto.getId_sala());
        if(!salaOpt.isPresent()){
            throw new ResourceNotFoundException("No existe una sala con ese id");
        }

        sesion.setNombre(oferta.getNombre()+" - Sesión "+order);
        sesion.setFecha(sesionDto.getFecha());
        sesion.setInicio(sesionDto.getInicio());
        sesion.setFin(sesionDto.getFin());
        sesion.setSala(salaOpt.get());
        sesion.setOfertaFormacion(oferta);

        for(Long idInstructor : sesionDto.getInstructores()) {
            Optional<Instructor> instructorOpt = instructorService.obtenerPorIdEntidad(idInstructor);
            if(!instructorOpt.isPresent()){
                throw new ResourceNotFoundException("No existe un instructor con ese id");
            }
            sesion.getInstructores().add(instructorOpt.get());
            instructorOpt.get().getSesiones().add(sesion);
        }

        return sesionRepository.save(sesion);
    }

    @Transactional
    public void eliminar(Sesion sesion) throws IOException {
        sesion.clearInstructores();
        sesionRepository.delete(sesion);
    }

    @Transactional
    public Sesion editar(Sesion sesion, SesionCreacionDTO sesionDto, int order) {


        Optional<Sala> salaOpt = salaService.obtenerPorIdEntidad(sesionDto.getId_sala());
        if(!salaOpt.isPresent()){
            throw new ResourceNotFoundException("No existe una sala con ese id");
        }

        sesion.setNombre(sesion.getOfertaFormacion().getNombre()+" - Sesión "+order);
        sesion.setFecha(sesionDto.getFecha());
        sesion.setInicio(sesionDto.getInicio());
        sesion.setFin(sesionDto.getFin());
        sesion.setSala(salaOpt.get());
        
        sesion.clearInstructores();

        sesion = sesionRepository.save(sesion);

        for(Long idInstructor : sesionDto.getInstructores()) {
            Optional<Instructor> instructorOpt = instructorService.obtenerPorIdEntidad(idInstructor);
            if(!instructorOpt.isPresent()){
                throw new ResourceNotFoundException("No existe un instructor con ese id");
            }
            sesion.addInstructor(instructorOpt.get());
        }

        return sesionRepository.save(sesion);
    }

}
