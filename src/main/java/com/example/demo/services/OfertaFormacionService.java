package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.request.OfertaCreacionDTO;
import com.example.demo.DTO.response.OfertaDetalleDTO;
import com.example.demo.DTO.response.OfertaItemDTO;
import com.example.demo.entities.CategoriaOferta;
import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.Institucion;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.TipoBeneficiario;
import com.example.demo.entities.TipoOferta;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CategoriaOfertaRepository;
import com.example.demo.repositories.InstitucionRepository;
import com.example.demo.repositories.OfertaFormacionRepository;
import com.example.demo.repositories.TipoBeneficiarioRepository;
import com.example.demo.repositories.TipoOfertaRepository;

import jakarta.transaction.Transactional;

@Service
public class OfertaFormacionService {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private OfertaFormacionRepository ofertaFormacionRepository;

    @Autowired
    private TipoOfertaRepository tipoOfertaRepository;

    @Autowired
    private CategoriaOfertaRepository categoriaOfertaRepository;

    @Autowired
    private TipoBeneficiarioRepository tipoBeneficiarioRepository;

    @Autowired
    private InstitucionRepository institucionRepository;

    @Transactional
    public OfertaDetalleDTO crear(OfertaCreacionDTO dto) {
        if (ofertaFormacionRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una oferta con el mismo código.");
        }

        TipoOferta tipo = tipoOfertaRepository.findById(dto.getId_tipo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de oferta no encontrado"));

        CategoriaOferta categoria = categoriaOfertaRepository.findById(dto.getId_categoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría de oferta no encontrada"));

        TipoBeneficiario tipoBeneficiario = tipoBeneficiarioRepository.findById(dto.getId_tipo_beneficiario())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de beneficiario no encontrado"));

        Institucion institucion = institucionRepository.findById(dto.getId_institucion())
                .orElseThrow(() -> new IllegalArgumentException("Institución no encontrada"));

        
        OfertaFormacion oferta = new OfertaFormacion();
        oferta.setNombre(dto.getNombre());
        oferta.setCodigo(dto.getCodigo());
        oferta.setCine(dto.getCine());
        oferta.setExtension(dto.isExtension());
        oferta.setEstado(EstadoOfertaFormacion.ACTIVA);
        oferta.setFechaInicio(dto.getFecha_inicio());
        oferta.setFechaFin(dto.getFecha_fin());
        oferta.setHoras(dto.getHoras());
        oferta.setTipo(tipo);
        oferta.setCategoria(categoria);
        oferta.setTipoBeneficiario(tipoBeneficiario);
        oferta.setValor(dto.getValor());
        oferta.setCupoMaximo(dto.getCupo_maximo());
        oferta.setSemestre(dto.getSemestre());
        oferta.setInstitucion(institucion);

        MultipartFile archivo = dto.getPieza_grafica();
        if (archivo != null && !archivo.isEmpty()) {
            try {
                byte[] bytes = archivo.getBytes();
                oferta.setPiezaGrafica(new String(bytes));
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la pieza gráfica", e);
            }
        }


        OfertaFormacion guardada = ofertaFormacionRepository.save(oferta);
        OfertaDetalleDTO detalleDTO = new OfertaDetalleDTO();
        detalleDTO.parseFromEntity(guardada);

        return detalleDTO;
    }

    public List<OfertaItemDTO> listarTodos() {
        return ofertaFormacionRepository.findAll().stream().map(oferta -> {
            OfertaItemDTO item = new OfertaItemDTO();
            item.parseFromEntity(oferta);
            return item;
        }).toList();
    }

    public List<OfertaItemDTO> listarParticipante(Long participanteId) {
        return inscripcionService.inscripcionesPorParticipante(participanteId).stream().map(inscripcion -> {
            OfertaItemDTO item = new OfertaItemDTO();
            item.parseFromEntity(inscripcion.getOfertaFormacion());
            return item;
        }).filter(oferta -> oferta.getEstado() != EstadoOfertaFormacion.INACTIVA).toList();
    }

    public List<OfertaItemDTO> listarInstructor(Long instructorId) {
        return ofertaFormacionRepository.findByInstructorId(instructorId).stream().map(oferta -> {
            OfertaItemDTO item = new OfertaItemDTO();
            item.parseFromEntity(oferta);
            return item;
        }).toList();
    }

    public Optional<OfertaFormacion> obtenerPorIdEntidad(Long ofertaId){
        return ofertaFormacionRepository.findById(ofertaId);
    }

    public OfertaDetalleDTO obtenerPorIdDetalle(Long ofertaId){
        Optional<OfertaFormacion> opt = this.obtenerPorIdEntidad(ofertaId);
        if(!opt.isPresent()){
            throw new ResourceNotFoundException("No existe una oferta de formación con ese id");
        }
        OfertaDetalleDTO dto = new OfertaDetalleDTO();
        dto.parseFromEntity(opt.get());
        return dto;
    }
}