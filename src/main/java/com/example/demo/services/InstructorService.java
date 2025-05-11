package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.InstructorDetalleDTO;
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
    private UsuarioService usuarioService;

    @Autowired
    private ModalidadService modalidadService;

    @Transactional
    public InstructorDetalleDTO crearInstructor(DatosPersonalesDTO instructorCreacionDTO) throws Exception {
        Instructor instructor = new Instructor();
        Usuario usuario;

        Optional<Modalidad> modalidadOpt = modalidadService
                .obtenerPorIdEntidad(Long.valueOf(instructorCreacionDTO.getId_modalidad()));
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorDocumento(instructorCreacionDTO.getDocumento());

        if (!modalidadOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una modalidad con ese id");
        }

        if (usuarioExistente.isPresent()) {
            usuario = usuarioExistente.get();
        } else {
            DatosPersonalesDTO ucdto = new DatosPersonalesDTO();
            ucdto.setCorreo_personal(instructorCreacionDTO.getCorreo_personal());
            ucdto.setDocumento(instructorCreacionDTO.getDocumento());
            ucdto.setFecha_expedicion(instructorCreacionDTO.getFecha_expedicion());
            ucdto.setFecha_nacimiento(instructorCreacionDTO.getFecha_nacimiento());
            ucdto.setId_municipio(instructorCreacionDTO.getId_municipio());
            ucdto.setId_pais(instructorCreacionDTO.getId_pais());
            ucdto.setId_tipo_documento(instructorCreacionDTO.getId_tipo_documento());
            ucdto.setPrimer_apellido(instructorCreacionDTO.getPrimer_apellido());
            ucdto.setPrimer_nombre(instructorCreacionDTO.getPrimer_nombre());
            ucdto.setSegundo_apellido(instructorCreacionDTO.getSegundo_apellido());
            ucdto.setSegundo_nombre(instructorCreacionDTO.getSegundo_nombre());
            ucdto.setSexo(instructorCreacionDTO.getSexo());
            ucdto.setTelefono(instructorCreacionDTO.getTelefono());
            usuario = usuarioService.crear(ucdto);
        }

        instructor.setActivo(true);
        instructor.setDireccion(instructorCreacionDTO.getDireccion());
        instructor.setEntidad(instructorCreacionDTO.getEntidad());
        instructor.setModalidad(modalidadOpt.get());
        instructor.setUsuario(usuario);

        instructor = instructorRepository.save(instructor);

        InstructorDetalleDTO idto = new InstructorDetalleDTO();
        idto.parseFromEntity(instructor);

        return idto;
    }

    @Transactional
    public InstructorDetalleDTO actualizar(Long id, DatosPersonalesDTO instructorDto) throws Exception {
        Optional<Instructor> instructorOpt = this.obtenerPorIdEntidad(id);

        if (!instructorOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un instructor con ese id");
        }

        Instructor instructor = instructorOpt.get();
        Usuario usuario;

        Optional<Modalidad> modalidadOpt = modalidadService
                .obtenerPorIdEntidad(Long.valueOf(instructorDto.getId_modalidad()));
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorDocumento(instructorDto.getDocumento());

        if (!modalidadOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una modalidad con ese id");
        }

        if (!usuarioExistente.isPresent()) {
            throw new ResourceNotFoundException("No existe un usuario con ese id");
        }

        ChangeMap changes = new ChangeMap();

        DatosPersonalesDTO ucdto = new DatosPersonalesDTO();
        ucdto.setCorreo_personal(instructorDto.getCorreo_personal());
        ucdto.setDocumento(instructorDto.getDocumento());
        ucdto.setFecha_expedicion(instructorDto.getFecha_expedicion());
        ucdto.setFecha_nacimiento(instructorDto.getFecha_nacimiento());
        ucdto.setId_municipio(instructorDto.getId_municipio());
        ucdto.setId_pais(instructorDto.getId_pais());
        ucdto.setId_tipo_documento(instructorDto.getId_tipo_documento());
        ucdto.setPrimer_apellido(instructorDto.getPrimer_apellido());
        ucdto.setPrimer_nombre(instructorDto.getPrimer_nombre());
        ucdto.setSegundo_apellido(instructorDto.getSegundo_apellido());
        ucdto.setSegundo_nombre(instructorDto.getSegundo_nombre());
        ucdto.setSexo(instructorDto.getSexo());
        ucdto.setTelefono(instructorDto.getTelefono());
        usuario = usuarioService.crear(ucdto);

        instructor.registerValues(changes, true);
        instructorDto.registerChanges(changes);

        usuarioService.actualizar(id, ucdto);

        instructor.setActivo(true);
        instructor.setDireccion(instructorDto.getDireccion());
        instructor.setEntidad(instructorDto.getEntidad());
        instructor.setModalidad(modalidadOpt.get());
        instructor.setUsuario(usuario);

        instructor = instructorRepository.save(instructor);

        InstructorDetalleDTO idto = new InstructorDetalleDTO();
        idto.parseFromEntity(instructor);

        return idto;
    }

    public InstructorDetalleDTO obtenerPorId(Long id) {
        InstructorDetalleDTO iDto = new InstructorDetalleDTO();
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un instructor con ese id"));

        iDto.parseFromEntity(instructor);
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
