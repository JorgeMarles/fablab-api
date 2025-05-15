package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.DTO.response.InstructorItemDTO;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.Modalidad;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.InstructorRepository;
import com.example.demo.utils.ChangeMap;

import jakarta.transaction.Transactional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ModalidadService modalidadService;

    @Transactional
    public DatosPersonalesResponseDTO crearInstructor(DatosPersonalesDTO instructorCreacionDTO, Usuario existente) throws Exception {
        Instructor instructor = new Instructor();

        Optional<Modalidad> modalidadOpt = modalidadService
                .obtenerPorIdEntidad(Long.valueOf(instructorCreacionDTO.getId_modalidad()));

        if (!modalidadOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una modalidad con ese id");
        } 
        instructor.setActivo(true);
        instructor.setDireccion(instructorCreacionDTO.getDireccion());
        instructor.setEntidad(instructorCreacionDTO.getEntidad());
        instructor.setModalidad(modalidadOpt.get());
        instructor.setUsuario(existente);

        instructor = instructorRepository.save(instructor);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(instructor.getUsuario());

        return idto;
    }

    @Transactional
    public void switchEstado(Long id) throws Exception {
        Optional<Instructor> instructorOpt = this.obtenerPorIdEntidad(id);

        if (!instructorOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un instructor con ese id");
        }

        Instructor instructor = instructorOpt.get();
        instructor.setActivo(!instructor.getActivo());
        instructorRepository.save(instructor);
    }

    @Transactional
    public DatosPersonalesResponseDTO actualizar(Long id, DatosPersonalesDTO instructorDto) throws Exception {
        Optional<Instructor> instructorOpt = this.obtenerPorIdEntidad(id);

        if (!instructorOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un instructor con ese id");
        }

        Instructor instructor = instructorOpt.get();

        Optional<Modalidad> modalidadOpt = modalidadService
                .obtenerPorIdEntidad(Long.valueOf(instructorDto.getId_modalidad()));

        if (!modalidadOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una modalidad con ese id");
        }
        ChangeMap changes = new ChangeMap();

        instructor.registerValues(changes, true);
        instructorDto.registerChanges(changes);

        instructor.setActivo(true);
        instructor.setDireccion(instructorDto.getDireccion());
        instructor.setEntidad(instructorDto.getEntidad());
        instructor.setModalidad(modalidadOpt.get());

        instructor = instructorRepository.save(instructor);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(instructor.getUsuario());

        return idto;
    }

    public DatosPersonalesResponseDTO obtenerPorId(Long id) {
        DatosPersonalesResponseDTO iDto = new DatosPersonalesResponseDTO();
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un instructor con ese id"));

        iDto.parseFromEntity(instructor.getUsuario());
        return iDto;
    }

    public List<InstructorItemDTO> listar() {
        List<InstructorItemDTO> instructoresResponse = instructorRepository.findAll().stream().map(instructor -> {
            InstructorItemDTO instructorResponse = new InstructorItemDTO();
            instructorResponse.parseFromEntity(instructor);
            return instructorResponse;
        }).toList();
        return instructoresResponse;
    }

    public List<InstructorItemDTO> listarPorSesionId(Long sesionId) {
        List<InstructorItemDTO> instructoresResponse = instructorRepository.findBySesiones_Id(sesionId).stream()
                .map(instructor -> {
                    InstructorItemDTO instructorResponse = new InstructorItemDTO();
                    instructorResponse.parseFromEntity(instructor);
                    return instructorResponse;
                }).toList();
        return instructoresResponse;
    }

    public boolean existe(Long id) {
        return instructorRepository.existsById(id);
    }

    public Optional<Instructor> obtenerPorIdEntidad(Long id) {
        return instructorRepository.findById(id);
    }

    public List<Instructor> listarEntidad() {
        return instructorRepository.findAll();
    }

    public List<Instructor> listarEntidadPorSesionId(Long sesionId) {
        return instructorRepository.findBySesiones_Id(sesionId);
    }
}
