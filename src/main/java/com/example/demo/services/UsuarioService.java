package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
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

    @Transactional
    public Usuario crear(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario crear(DatosPersonalesDTO usuarioDto) {
        
        Usuario usuario = new Usuario();

        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_tipo_documento()));
        Optional<Pais> paisOpt = paisService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_pais()));
        Optional<Municipio> municipioOpt = municipioService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_municipio()));
        if(!tipoDocumentoOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un tipo de documento con ese id");
        }

        if(!paisOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un pais con ese id");
        }

        if(!municipioOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un municipio con ese id");
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
    public Usuario actualizar(Long id, DatosPersonalesDTO usuarioDto){
        Usuario usuario = this.buscarPorId(id);

        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_tipo_documento()));
        Optional<Pais> paisOpt = paisService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_pais()));
        Optional<Municipio> municipioOpt = municipioService.buscarPorIdEntidad(Long.valueOf(usuarioDto.getId_municipio()));
        if(!tipoDocumentoOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un tipo de documento con ese id");
        }

        if(!paisOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un pais con ese id");
        }

        if(!municipioOpt.isPresent()){
            throw new ResourceNotFoundException("No existe un municipio con ese id");
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

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    public Optional<Usuario> buscarPorDocumento(String documento){
        return usuarioRepository.findByDocumento(documento);
    }
}
