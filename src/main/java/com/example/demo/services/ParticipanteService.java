package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.DTO.response.ParticipanteDetalleDTO;
import com.example.demo.DTO.response.ParticipanteItemDTO;
import com.example.demo.entities.EstadoCivil;
import com.example.demo.entities.Participante;
import com.example.demo.entities.PoblacionEspecial;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ParticipanteRepository;
import com.example.demo.utils.ChangeMap;

import jakarta.transaction.Transactional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EstadoCivilService estadoCivilService;

    @Autowired
    private PoblacionEspecialService poblacionEspecialService;

    @Transactional
    public DatosPersonalesResponseDTO crearParticipante(DatosPersonalesDTO participanteCreacionDTO, Usuario existente)
            throws Exception {
        Participante participante = new Participante();

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
        participante.setUsuario(existente);

        participante = participanteRepository.save(participante);

        DatosPersonalesResponseDTO idto = new DatosPersonalesResponseDTO();
        idto.parseFromEntity(participante.getUsuario());

        return idto;
    }

    public ParticipanteDetalleDTO actualizar(Long id, DatosPersonalesDTO participanteDto) throws Exception {
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

        ChangeMap changes = new ChangeMap();

        participante.registerValues(changes, true);
        participanteDto.registerChanges(changes);

        participante.setCorreoInstitucional(participanteDto.getCorreo_institucional());
        participante.setDireccionInstitucional(participanteDto.getDireccion_institucional());
        participante.setEstadoCivil(estadoOpt.get());
        participante.setPoblacionEspecial(poblacionEsOpt.get());

        participante = participanteRepository.save(participante);

        ParticipanteDetalleDTO idto = new ParticipanteDetalleDTO();
        idto.parseFromEntity(participante);

        return idto;
    }

    public ParticipanteDetalleDTO obtenerPorId(Long id) {
        ParticipanteDetalleDTO iDto = new ParticipanteDetalleDTO();
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un participante con ese id"));

        iDto.parseFromEntity(participante);
        return iDto;
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
