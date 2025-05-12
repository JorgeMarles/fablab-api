package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.DatosPersonalesResponseDTO;
import com.example.demo.entities.Municipio;
import com.example.demo.entities.Pais;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private InstructorService instructorService;

    @Transactional
    public DatosPersonalesResponseDTO crearParticipante(DatosPersonalesDTO usuarioDto, Usuario usuario) throws Exception {
        Usuario usuarioCreado = this.crear(usuarioDto, usuario);
        return participanteService.crearParticipante(usuarioDto, usuarioCreado);
    }

    @Transactional
    public DatosPersonalesResponseDTO crearInstructor(DatosPersonalesDTO usuarioDto, Usuario usuario) throws Exception {
        Usuario usuarioCreado = this.crear(usuarioDto, usuario);
        return instructorService.crearInstructor(usuarioDto, usuarioCreado);
    }

    @Transactional
    public Usuario crear(DatosPersonalesDTO usuarioDto, Usuario usuario) {
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoService
                .obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_tipo_documento()));
        Optional<Pais> paisOpt = paisService.obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_pais()));
        Optional<Municipio> municipioOpt = municipioService
                .obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_municipio()));
        if (!tipoDocumentoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un tipo de documento con ese id");
        }

        if (!paisOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un pais con ese id");
        }

        if (!municipioOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un municipio con ese id");
        }

        if(usuario == null) {
            usuario = new Usuario();
        }

        usuario.setCorreoPersonal(usuarioDto.getCorreo_personal());
        usuario.setDocumento(usuarioDto.getDocumento());
        usuario.setFechaExpedicion(usuarioDto.getFecha_expedicion());
        usuario.setFechaNacimiento(usuarioDto.getFecha_nacimiento());
        usuario.setMunicipio(municipioOpt.get());
        usuario.setPais(paisOpt.get());
        usuario.setPrimerApellido(usuarioDto.getPrimer_apellido());
        usuario.setPrimerNombre(usuarioDto.getPrimer_nombre());
        usuario.setSegundoApellido(usuarioDto.getSegundo_apellido());
        usuario.setSegundoNombre(usuarioDto.getSegundo_nombre());
        usuario.setSexo(usuarioDto.getSexo());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setTipoDocumento(tipoDocumentoOpt.get());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Long id, DatosPersonalesDTO usuarioDto) throws Exception {
        Usuario usuario = this.obtenerPorIdEntidad(id);

        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoService
                .obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_tipo_documento()));
        Optional<Pais> paisOpt = paisService.obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_pais()));
        Optional<Municipio> municipioOpt = municipioService
                .obtenerPorIdEntidad(Long.valueOf(usuarioDto.getId_municipio()));
        if (!tipoDocumentoOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un tipo de documento con ese id");
        }

        if (!paisOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un pais con ese id");
        }

        if (!municipioOpt.isPresent()) {
            throw new ResourceNotFoundException("No existe un municipio con ese id");
        }

        usuario.setDocumento(usuarioDto.getDocumento());
        usuario.setFechaExpedicion(usuarioDto.getFecha_expedicion());
        usuario.setFechaNacimiento(usuarioDto.getFecha_nacimiento());
        usuario.setMunicipio(municipioOpt.get());
        usuario.setPais(paisOpt.get());
        usuario.setPrimerApellido(usuarioDto.getPrimer_apellido());
        usuario.setPrimerNombre(usuarioDto.getPrimer_nombre());
        usuario.setSegundoApellido(usuarioDto.getSegundo_apellido());
        usuario.setSegundoNombre(usuarioDto.getSegundo_nombre());
        usuario.setSexo(usuarioDto.getSexo());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setTipoDocumento(tipoDocumentoOpt.get());

        if(usuario.getParticipante() != null) {
            participanteService.actualizar(usuario.getParticipante().getId(), usuarioDto);
        } else if (usuario.getInstructor() != null) {
            instructorService.actualizar(usuario.getInstructor().getId(), usuarioDto);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorIdEntidad(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    public Optional<Usuario> buscarPorDocumento(String documento) {
        return usuarioRepository.findByDocumento(documento);
    }

    public DatosPersonalesResponseDTO obtenerPorId(Long usuarioId) {
        Usuario usuario = this.obtenerPorIdEntidad(usuarioId);
        DatosPersonalesResponseDTO dto = new DatosPersonalesResponseDTO();
        dto.parseFromEntity(usuario);
        return dto;
    }
}
