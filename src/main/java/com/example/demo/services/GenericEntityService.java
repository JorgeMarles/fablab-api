package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.CargoDTO;
import com.example.demo.DTO.response.CategoriaDTO;
import com.example.demo.DTO.response.EstadoCivilDTO;
import com.example.demo.DTO.response.IResponseDTO;
import com.example.demo.DTO.response.InstitucionDTO;
import com.example.demo.DTO.response.ModalidadDTO;
import com.example.demo.DTO.response.MunicipioDTO;
import com.example.demo.DTO.response.PaisDTO;
import com.example.demo.DTO.response.PoblacionEspecialDTO;
import com.example.demo.DTO.response.ProgramaAcademicoDTO;
import com.example.demo.DTO.response.SalaDTO;
import com.example.demo.DTO.response.SemilleroDTO;
import com.example.demo.DTO.response.TipoBeneficiarioDTO;
import com.example.demo.DTO.response.TipoDocumentoDTO;
import com.example.demo.DTO.response.TipoInstitucionDTO;
import com.example.demo.DTO.response.TipoOfertaDTO;
import com.example.demo.entities.BaseEntity;
import com.example.demo.entities.Cargo;
import com.example.demo.entities.CategoriaOferta;
import com.example.demo.entities.EstadoCivil;
import com.example.demo.entities.Institucion;
import com.example.demo.entities.Modalidad;
import com.example.demo.entities.Municipio;
import com.example.demo.entities.Pais;
import com.example.demo.entities.PoblacionEspecial;
import com.example.demo.entities.ProgramaAcademico;
import com.example.demo.entities.Sala;
import com.example.demo.entities.Semillero;
import com.example.demo.entities.TipoBeneficiario;
import com.example.demo.entities.TipoDocumento;
import com.example.demo.entities.TipoInstitucion;
import com.example.demo.entities.TipoOferta;
import com.example.demo.repositories.GenericRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class GenericEntityService {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Mapper {
        Class<? extends BaseEntity> entity;
        Class<? extends IResponseDTO<?>> dto;
    }

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TipoInstitucionService tipoInstitucionService;

    Logger logger = LoggerFactory.getLogger(GenericEntityService.class);

    private final Map<String, Mapper> entityTypeMap = new HashMap<>();

    public GenericEntityService() {
        entityTypeMap.put("cargo", new Mapper(Cargo.class, CargoDTO.class));
        entityTypeMap.put("categoriaOferta", new Mapper(CategoriaOferta.class, CategoriaDTO.class));
        entityTypeMap.put("estadoCivil", new Mapper(EstadoCivil.class, EstadoCivilDTO.class));
        entityTypeMap.put("institucion", new Mapper(Institucion.class, InstitucionDTO.class));
        entityTypeMap.put("modalidad", new Mapper(Modalidad.class, ModalidadDTO.class));
        entityTypeMap.put("municipio", new Mapper(Municipio.class, MunicipioDTO.class));
        entityTypeMap.put("pais", new Mapper(Pais.class, PaisDTO.class));
        entityTypeMap.put("poblacionEspecial", new Mapper(PoblacionEspecial.class, PoblacionEspecialDTO.class));
        entityTypeMap.put("programaAcademico", new Mapper(ProgramaAcademico.class, ProgramaAcademicoDTO.class));
        entityTypeMap.put("sala", new Mapper(Sala.class, SalaDTO.class));
        entityTypeMap.put("semillero", new Mapper(Semillero.class, SemilleroDTO.class));
        entityTypeMap.put("tipoBeneficiario", new Mapper(TipoBeneficiario.class, TipoBeneficiarioDTO.class));
        entityTypeMap.put("tipoDocumento", new Mapper(TipoDocumento.class, TipoDocumentoDTO.class));
        entityTypeMap.put("tipoInstitucion", new Mapper(TipoInstitucion.class, TipoInstitucionDTO.class));
        entityTypeMap.put("tipoOferta", new Mapper(TipoOferta.class, TipoOfertaDTO.class));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public IResponseDTO createEntity(String entityType, Map<String, Object> data) throws Exception {
        Mapper mapper = entityTypeMap.get(entityType);
        if (mapper == null) {
            throw new IllegalArgumentException("Tipo de entidad no soportado: " + entityType);
        }

        try {
            Class<? extends BaseEntity> entityClass = mapper.getEntity();
            Class<? extends IResponseDTO<?>> dtoClass = mapper.getDto();
            BaseEntity entity = entityClass.getDeclaredConstructor().newInstance();

            if (data.containsKey("nombre")) {
                entity.setNombre((String) data.get("nombre"));
            }

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!entry.getKey().equals("id") && !entry.getKey().equals("nombre")) {
                    try {
                        if (entry.getKey().equals("id_tipo_institucion")) {
                            if (entityType.equals("institucion")) {
                                Long idTipoInstitucion = Long.parseLong(entry.getValue().toString());
                                Optional<TipoInstitucion> tipoInstitucion = tipoInstitucionService
                                        .buscarPorIdEntidad(idTipoInstitucion);
                                if (tipoInstitucion.isPresent()) {
                                    entityClass.getMethod("setTipoInstitucion", TipoInstitucion.class)
                                            .invoke(entity, tipoInstitucion.get());
                                } else {
                                    throw new IllegalArgumentException("No existe un tipo de Institución con ese id");
                                }
                            }
                        } else {
                            entityClass.getMethod("set" + capitalize(entry.getKey()), String.class)
                                    .invoke(entity, entry.getValue().toString());
                        }
                    } catch (NoSuchMethodException e) {
                        logger.error("No se encuenta el campo " + entry.getKey() + " en la entidad "
                                + entityClass.getSimpleName(), e);
                        throw new IllegalArgumentException("No se encuenta el campo " + entry.getKey() + " en la entidad "
                                + entityClass.getSimpleName(), e);
                    }
                }
            }

            // Obtiene el repositorio apropiado y guarda la entidad
            String repositoryName = entityClass.getSimpleName() + "Repository";
            repositoryName = Character.toLowerCase(repositoryName.charAt(0)) + repositoryName.substring(1);

            GenericRepository<BaseEntity> repository = (GenericRepository<BaseEntity>) context.getBean(repositoryName);
            BaseEntity saved = repository.save(entity);
            IResponseDTO dto = dtoClass.getDeclaredConstructor().newInstance();
            dto.parseFromEntity(saved);
            return dto;

        } catch (Exception e) {
            logger.error("Error al crear entidad: ", e);
            throw e;
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}