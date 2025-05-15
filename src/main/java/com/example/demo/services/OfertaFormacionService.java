package com.example.demo.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.OfertaCreacionDTO;
import com.example.demo.DTO.request.SesionCreacionDTO;
import com.example.demo.DTO.response.OfertaDetalleDTO;
import com.example.demo.DTO.response.OfertaItemDTO;
import com.example.demo.entities.Archivo;
import com.example.demo.entities.CategoriaOferta;
import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.Institucion;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.entities.Sesion;
import com.example.demo.entities.TipoBeneficiario;
import com.example.demo.entities.TipoOferta;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CategoriaOfertaRepository;
import com.example.demo.repositories.InstitucionRepository;
import com.example.demo.repositories.OfertaFormacionRepository;
import com.example.demo.repositories.TipoBeneficiarioRepository;
import com.example.demo.repositories.TipoOfertaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

    @Autowired
    private SesionService sesionService;

    @Autowired
    private CertificadoService certificadoService;

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private FileService fileService;

    public OfertaDetalleDTO crearRetDto(OfertaCreacionDTO dto) {
        OfertaDetalleDTO detalleDTO = new OfertaDetalleDTO();
        OfertaFormacion of = this.crear(dto);
        detalleDTO.parseFromEntity(of);
        return detalleDTO;
    }

    @Transactional
    public OfertaFormacion switchEstado(Long idOferta) {
        OfertaFormacion oferta = ofertaFormacionRepository.findById(idOferta)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una oferta de formación con ese id"));
        if (oferta.getEstado() == EstadoOfertaFormacion.ACTIVA) {
            oferta.setEstado(EstadoOfertaFormacion.INACTIVA);
        } else if (oferta.getEstado() == EstadoOfertaFormacion.INACTIVA) {
            oferta.setEstado(EstadoOfertaFormacion.ACTIVA);
        }
        return ofertaFormacionRepository.save(oferta);
    }

    @Transactional
    public OfertaFormacion crear(OfertaCreacionDTO dto) {
        String fileUuid = null;
        try {
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

            if (dto.getPieza_grafica() != null) {
                Archivo archivo = fileService.uploadFile(dto.getPieza_grafica());
                fileUuid = archivo.getUuid();
                oferta.setPiezaGrafica(archivo);
            } else {
                throw new IllegalArgumentException("No se ha subido una pieza gráfica");
            }

            OfertaFormacion guardada = ofertaFormacionRepository.save(oferta);
            log.info("Sesiones: " + dto.getSesiones().size());
            int order = 1;
            for (SesionCreacionDTO sesion : dto.getSesiones()) {
                Sesion creada = sesionService.crear(sesion, oferta, order++);
                oferta.getSesiones().add(creada);
            }
            log.info("Oferta guardada: " + guardada.getId());
            return guardada;
        } catch (FileException | IOException ex) {
            if (fileUuid != null) {
                try {
                    fileService.deleteFile(fileUuid);
                } catch (Exception e) {
                    log.error("Error al eliminar el archivo temporal: " + e.getMessage());
                }
            }
            throw new FileException("Error al subir la pieza gráfica: " + ex.getMessage());
        }
    }

    @Transactional
    public OfertaFormacion editar(Long idOferta, OfertaCreacionDTO ofertaDto) {
        String fileUuid = null;
        try {
            OfertaFormacion oferta = obtenerPorIdEntidad(idOferta)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe una oferta de formación con ese id"));

            // Campos estaticos
            oferta.setNombre(ofertaDto.getNombre());
            oferta.setCodigo(ofertaDto.getCodigo());
            oferta.setCine(ofertaDto.getCine());
            oferta.setExtension(ofertaDto.isExtension());
            oferta.setEstado(EstadoOfertaFormacion.ACTIVA);
            oferta.setFechaInicio(ofertaDto.getFecha_inicio());
            oferta.setFechaFin(ofertaDto.getFecha_fin());
            oferta.setHoras(ofertaDto.getHoras());
            oferta.setValor(ofertaDto.getValor());
            oferta.setCupoMaximo(ofertaDto.getCupo_maximo());
            oferta.setSemestre(ofertaDto.getSemestre());

            // Campos de referencia
            TipoOferta tipo = tipoOfertaRepository.findById(ofertaDto.getId_tipo())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de oferta no encontrado"));
            oferta.setTipo(tipo);
            CategoriaOferta categoria = categoriaOfertaRepository.findById(ofertaDto.getId_categoria())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría de oferta no encontrada"));
            oferta.setCategoria(categoria);
            TipoBeneficiario tipoBeneficiario = tipoBeneficiarioRepository.findById(ofertaDto.getId_tipo_beneficiario())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de beneficiario no encontrado"));
            oferta.setTipoBeneficiario(tipoBeneficiario);
            Institucion institucion = institucionRepository.findById(ofertaDto.getId_institucion())
                    .orElseThrow(() -> new IllegalArgumentException("Institución no encontrada"));
            oferta.setInstitucion(institucion);

            // Campos de archivo
            if (ofertaDto.getPieza_grafica() != null) {
                // Store the old archivo UUID for later deletion
                String oldArchivoUuid = null;
                if (oferta.getPiezaGrafica() != null) {
                    oldArchivoUuid = oferta.getPiezaGrafica().getUuid();
                }

                // Upload new file and create new archivo
                Archivo archivo = fileService.uploadFile(ofertaDto.getPieza_grafica());

                fileUuid = archivo.getUuid();

                // Update relationship
                oferta.setPiezaGrafica(archivo);

                // Save to update relationships in database
                oferta = ofertaFormacionRepository.save(oferta);

                // Now it's safe to delete the old file (after DB relationship is updated)
                if (oldArchivoUuid != null) {
                    try {
                        fileService.deleteFile(oldArchivoUuid);
                    } catch (Exception e) {
                        log.error("Error al eliminar el archivo anterior: " + e.getMessage());
                    }
                }
            }

            List<Sesion> sesionesARemover = oferta.getSesiones().stream()
                    .filter(sesion -> ofertaDto.getSesiones().stream()
                            .noneMatch(s -> s.getId() != null && s.getId().equals(sesion.getId())))
                    .collect(Collectors.toList());

            for (Sesion sesion : sesionesARemover) {
                log.info("Eliminando sesión: " + sesion.getId());
                sesionService.eliminar(sesion);
                oferta.getSesiones().remove(sesion);
            }

            int order = 1;
            for (SesionCreacionDTO sesionDto : ofertaDto.getSesiones()) {
                if (sesionDto.getId() != null) {
                    Sesion sesion = oferta.getSesiones().stream()
                            .filter(s -> s.getId() != null && s.getId().equals(sesionDto.getId())).findFirst()
                            .orElseThrow(
                                    () -> new ResourceNotFoundException(
                                            "No existe una sesión con id " + sesionDto.getId()));
                    sesionService.editar(sesion, sesionDto, order++);
                } else {
                    Sesion nuevaSesion = sesionService.crear(sesionDto, oferta, order++);
                    oferta.getSesiones().add(nuevaSesion);
                }
            }

            return ofertaFormacionRepository.save(oferta);
        } catch (FileException | IOException ex) {
            if (fileUuid != null) {
                try {
                    fileService.deleteFile(fileUuid);
                } catch (Exception e) {
                    log.error("Error al eliminar el archivo temporal: " + e.getMessage());
                }
            }
            throw new FileException("Error al subir la pieza gráfica: " + ex.getMessage());
        }
    }

    @Transactional
    public void finalizar(Long ofertaId, Long plantillaId) {

        PlantillaCertificado plantilla = plantillaService.obtenerPorId(plantillaId);

        OfertaFormacion oferta = ofertaFormacionRepository.findById(ofertaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una oferta de formación con ese id"));
        oferta.setEstado(EstadoOfertaFormacion.FINALIZADA);
        LocalDate fechaFin = LocalDate.now();
        oferta.getSesiones().forEach(sesion -> {
            sesion.getInstructores().forEach(instructor -> {
                certificadoService.crearCertificado(oferta, instructor, fechaFin, plantilla);
            });
        });
        ofertaFormacionRepository.save(oferta);
    }

    public List<OfertaItemDTO> listarTodos() {
        return ofertaFormacionRepository.findAll().stream().map(oferta -> {
            OfertaItemDTO item = new OfertaItemDTO();
            item.parseFromEntity(oferta);
            return item;
        }).toList();
    }

    public List<OfertaItemDTO> listarPorCategoria(Long categoriaId) {
        return ofertaFormacionRepository.findByCategoria_Id(categoriaId).stream().map(oferta -> {
            OfertaItemDTO item = new OfertaItemDTO();
            item.parseFromEntity(oferta);
            return item;
        }).filter(oferta -> oferta.getEstado() != EstadoOfertaFormacion.INACTIVA).toList();
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

    public Optional<OfertaFormacion> obtenerPorIdEntidad(Long ofertaId) {
        return ofertaFormacionRepository.findById(ofertaId);
    }

    public OfertaDetalleDTO obtenerPorIdDetalle(Long ofertaId) {
        Optional<OfertaFormacion> opt = this.obtenerPorIdEntidad(ofertaId);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("No existe una oferta de formación con ese id");
        }
        OfertaDetalleDTO dto = new OfertaDetalleDTO();
        dto.parseFromEntity(opt.get());
        return dto;
    }
}