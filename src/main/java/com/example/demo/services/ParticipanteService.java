package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
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
    private UsuarioService usuarioService;

    @Autowired
    private EstadoCivilService estadoCivilService;

    @Autowired
    private PoblacionEspecialService poblacionEspecialService;

    @Transactional
    public ParticipanteDetalleDTO crearParticipante(DatosPersonalesDTO participanteCreacionDTO) {
        Participante participante = new Participante();
        Usuario usuario;

        Optional<EstadoCivil> estadoOpt = estadoCivilService
                .buscarPorIdEntidad(participanteCreacionDTO.getId_estado_civil());
        Optional<PoblacionEspecial> poblacionEsOpt = poblacionEspecialService
                .buscarPorIdEntidad(participanteCreacionDTO.getId_poblacion_especial());
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorDocumento(participanteCreacionDTO.getDocumento());

        if (!estadoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un estado civil con ese id");
        }

        if (!poblacionEsOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una población especial con ese id");
        }

        if (usuarioExistente.isPresent()) {
            usuario = usuarioExistente.get();
        } else {
            DatosPersonalesDTO ucdto = new DatosPersonalesDTO();
            ucdto.setCorreo_personal(participanteCreacionDTO.getCorreo_personal());
            ucdto.setDocumento(participanteCreacionDTO.getDocumento());
            ucdto.setFecha_expedicion(participanteCreacionDTO.getFecha_expedicion());
            ucdto.setFecha_nacimiento(participanteCreacionDTO.getFecha_nacimiento());
            ucdto.setId_municipio(participanteCreacionDTO.getId_municipio());
            ucdto.setId_pais(participanteCreacionDTO.getId_pais());
            ucdto.setId_tipo_documento(participanteCreacionDTO.getId_tipo_documento());
            ucdto.setPrimer_apellido(participanteCreacionDTO.getPrimer_apellido());
            ucdto.setPrimer_nombre(participanteCreacionDTO.getPrimer_nombre());
            ucdto.setSegundo_apellido(participanteCreacionDTO.getSegundo_apellido());
            ucdto.setSegundo_nombre(participanteCreacionDTO.getSegundo_nombre());
            ucdto.setSexo(participanteCreacionDTO.getSexo());
            ucdto.setTelefono(participanteCreacionDTO.getTelefono());
            usuario = usuarioService.crear(ucdto);
        }

        participante.setCorreoInstitucional(participanteCreacionDTO.getCorreo_institucional());
        participante.setDireccionInstitucional(participanteCreacionDTO.getDireccion_institucional());
        participante.setEstadoCivil(estadoOpt.get());
        participante.setPoblacionEspecial(poblacionEsOpt.get());
        participante.setUsuario(usuario);

        participante = participanteRepository.save(participante);

        ParticipanteDetalleDTO idto = new ParticipanteDetalleDTO();
        idto.parseFromEntity(participante);

        return idto;
    }

    public ParticipanteDetalleDTO actualizar(Long id, DatosPersonalesDTO participanteDto) throws Exception {
        Optional<Participante> participanteOpt = this.buscarPorIdEntidad(id);

        if (!participanteOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un participante con ese id");
        }

        Participante participante = participanteOpt.get();
        Usuario usuario;

        Optional<EstadoCivil> estadoOpt = estadoCivilService.buscarPorIdEntidad(participanteDto.getId_estado_civil());
        Optional<PoblacionEspecial> poblacionEsOpt = poblacionEspecialService
                .buscarPorIdEntidad(participanteDto.getId_poblacion_especial());
        Optional<Usuario> usuarioExistente = usuarioService.buscarPorDocumento(participanteDto.getDocumento());

        if (!estadoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un estado civil con ese id");
        }

        if (!poblacionEsOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe una población especial con ese id");
        }

        if(!usuarioExistente.isPresent()){
            throw new ResourceNotFoundException("No existe un usuario con ese id");
        }

        ChangeMap changes = new ChangeMap();

        DatosPersonalesDTO ucdto = new DatosPersonalesDTO();
        ucdto.setCorreo_personal(participanteDto.getCorreo_personal());
        ucdto.setDocumento(participanteDto.getDocumento());
        ucdto.setFecha_expedicion(participanteDto.getFecha_expedicion());
        ucdto.setFecha_nacimiento(participanteDto.getFecha_nacimiento());
        ucdto.setId_municipio(participanteDto.getId_municipio());
        ucdto.setId_pais(participanteDto.getId_pais());
        ucdto.setId_tipo_documento(participanteDto.getId_tipo_documento());
        ucdto.setPrimer_apellido(participanteDto.getPrimer_apellido());
        ucdto.setPrimer_nombre(participanteDto.getPrimer_nombre());
        ucdto.setSegundo_apellido(participanteDto.getSegundo_apellido());
        ucdto.setSegundo_nombre(participanteDto.getSegundo_nombre());
        ucdto.setSexo(participanteDto.getSexo());
        ucdto.setTelefono(participanteDto.getTelefono());

        usuario = usuarioExistente.get();
        participante.registerValues(changes, true);
        participanteDto.registerChanges(changes);

        usuarioService.actualizar(id, ucdto);

        participante.setCorreoInstitucional(participanteDto.getCorreo_institucional());
        participante.setDireccionInstitucional(participanteDto.getDireccion_institucional());
        participante.setEstadoCivil(estadoOpt.get());
        participante.setPoblacionEspecial(poblacionEsOpt.get());
        participante.setUsuario(usuario);

        participante = participanteRepository.save(participante);

        

        ParticipanteDetalleDTO idto = new ParticipanteDetalleDTO();
        idto.parseFromEntity(participante);

        return idto;
    }

    public ParticipanteDetalleDTO buscarPorId(Long id) {
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

    public Optional<Participante> buscarPorIdEntidad(Long id) {
        return participanteRepository.findById(id);
    }

    public List<Participante> listarEntidad() {
        return participanteRepository.findAll();
    }

}
