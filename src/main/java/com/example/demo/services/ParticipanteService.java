package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.DTO.response.ParticipanteItemDTO;
import com.example.demo.entities.EstadoCivil;
import com.example.demo.entities.Participante;
import com.example.demo.entities.PoblacionEspecial;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ParticipanteRepository;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EstadoCivilService estadoCivilService;

    @Autowired
    private PoblacionEspecialService poblacionEspecialService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public DatosPersonalesResponseDTO crearParticipante(DatosPersonalesDTO participanteCreacionDTO, Usuario existente) {
        // Load the latest Usuario state
        existente = usuarioRepository.findById(existente.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con ese id"));

        Participante participante;

        // Check if Usuario already has a Participante
        if (existente.getParticipante() != null) {
            // Use the existing participante
            participante = existente.getParticipante();

            // Make sure it's managed by the current session
            if (!entityManager.contains(participante)) {
                participante = entityManager.merge(participante);
            }
        } else {
            // Create new participante
            participante = new Participante();
            participante.setUsuario(existente);
            existente.setParticipante(participante);
        }

        // Set properties
        Optional<EstadoCivil> estadoOpt = estadoCivilService
                .obtenerPorIdEntidad(participanteCreacionDTO.getId_estado_civil());
        Optional<PoblacionEspecial> poblacionEsOpt = poblacionEspecialService
                .obtenerPorIdEntidad(participanteCreacionDTO.getId_poblacion_especial());

        if (!estadoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un estado civil con ese id");
        }

        if (!poblacionEsOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una población especial con ese id");
        }

        participante.setCorreoInstitucional(participanteCreacionDTO.getCorreo_institucional());
        participante.setDireccionInstitucional(participanteCreacionDTO.getDireccion_institucional());
        participante.setEstadoCivil(estadoOpt.get());
        participante.setPoblacionEspecial(poblacionEsOpt.get());

        // Save and flush to ensure changes are persistent
        participante = participanteRepository.saveAndFlush(participante);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(participante.getUsuario());

        return idto;
    }

    public DatosPersonalesResponseDTO actualizar(Long id, DatosPersonalesDTO participanteDto) throws Exception {
        Optional<Participante> participanteOpt = this.obtenerPorIdEntidad(id);

        if (!participanteOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un participante con ese id");
        }

        Participante participante = participanteOpt.get();

        Optional<EstadoCivil> estadoOpt = estadoCivilService.obtenerPorIdEntidad(participanteDto.getId_estado_civil());
        Optional<PoblacionEspecial> poblacionEsOpt = poblacionEspecialService
                .obtenerPorIdEntidad(participanteDto.getId_poblacion_especial());

        if (!estadoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un estado civil con ese id");
        }

        if (!poblacionEsOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una población especial con ese id");
        }

        participante.setCorreoInstitucional(participanteDto.getCorreo_institucional());
        participante.setDireccionInstitucional(participanteDto.getDireccion_institucional());
        participante.setEstadoCivil(estadoOpt.get());
        participante.setPoblacionEspecial(poblacionEsOpt.get());

        participante = participanteRepository.save(participante);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(participante.getUsuario());

        return idto;
    }

    public DatosPersonalesResponseDTO obtenerPorId(Long id) {
        DatosPersonalesResponseDTO iDto = new DatosPersonalesResponseDTO();
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un participante con ese id"));

        iDto.parseFromEntity(participante.getUsuario());
        return iDto;
    }

    public List<Participante> buscar(String q) {
        List<Participante> participantes = participanteRepository.findBySearch(q);
        return participantes;
    }

    public List<ParticipanteItemDTO> listar() {
        List<ParticipanteItemDTO> participanteesResponse = participanteRepository.findAll().stream()
                .map(participante -> {
                    ParticipanteItemDTO participanteResponse = new ParticipanteItemDTO();
                    participanteResponse.parseFromEntity(participante);
                    return participanteResponse;
                }).toList();
        return participanteesResponse;
    }

    public boolean existe(Long id) {
        return participanteRepository.existsById(id);
    }

    public Optional<Participante> obtenerPorIdEntidad(Long id) {
        return participanteRepository.findById(id);
    }

    public List<Participante> listarEntidad() {
        return participanteRepository.findAll();
    }

}
