package com.example.demo.services;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.entities.BaseEntity;
import com.example.demo.entities.HistoricoUsuario;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.Participante;
import com.example.demo.entities.TipoDato;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.GenericRepository;
import com.example.demo.repositories.HistoricoUsuarioRepository;
import com.example.demo.utils.FieldChangeMetadata;

@Service
public class HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioRepository hUsuarioRepository;

    @Autowired
    private ApplicationContext context;

    public void crearCambio(Usuario usuario, String campo, FieldChangeMetadata fcm) {
        HistoricoUsuario hu = new HistoricoUsuario();
        hu.setCampo(campo);
        hu.setFechaModificacion(LocalDateTime.now());
        hu.setTipo(fcm.getType());
        hu.setUsuario(usuario);
        hu.setValorAnterior(fcm.getOldValue().toString());
        hu.setValorNuevo(fcm.getNewValue().toString());

        hUsuarioRepository.save(hu);
    }

    private void obtenerUsuarioEnFecha(Usuario usuario, LocalDateTime fecha)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<String> campos = Usuario.getFields();

        for (String campo : campos) {
            Optional<HistoricoUsuario> huOpt = hUsuarioRepository.findLatestHistoryBeforeDate(usuario.getId(), campo,
                    fecha);
            if (!huOpt.isPresent()) {
                continue;
            }
            HistoricoUsuario hu = huOpt.get();
            String setter = "set" + capitalize(hu.getCampo());
            Object valor = getValue(hu.getCampo(), hu.getTipo(), hu.getValorNuevo());
            if (valor == null) {
                continue;
            }
            Usuario.class.getMethod(setter, valor.getClass()).invoke(usuario, valor);
        }

    }

    public void obtenerInstructorEnFecha(Participante participante, LocalDateTime fecha)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<String> campos = Participante.getFields();

        for (String campo : campos) {
            Optional<HistoricoUsuario> huOpt = hUsuarioRepository.findLatestHistoryBeforeDate(participante.getId(), campo,
                    fecha);
            if (!huOpt.isPresent()) {
                continue;
            }
            HistoricoUsuario hu = huOpt.get();
            String setter = "set" + capitalize(hu.getCampo());
            Object valor = getValue(hu.getCampo(), hu.getTipo(), hu.getValorNuevo());
            if (valor == null) {
                continue;
            }
            Participante.class.getMethod(setter, valor.getClass()).invoke(participante, valor);
        }

        this.obtenerUsuarioEnFecha(participante.getUsuario(), fecha);
    }

    public void obtenerInstructorEnFecha(Instructor instructor, LocalDateTime fecha)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<String> campos = Instructor.getFields();

        for (String campo : campos) {
            Optional<HistoricoUsuario> huOpt = hUsuarioRepository.findLatestHistoryBeforeDate(instructor.getId(), campo,
                    fecha);
            if (!huOpt.isPresent()) {
                continue;
            }
            HistoricoUsuario hu = huOpt.get();
            String setter = "set" + capitalize(hu.getCampo());
            Object valor = getValue(hu.getCampo(), hu.getTipo(), hu.getValorNuevo());
            if (valor == null) {
                continue;
            }
            Instructor.class.getMethod(setter, valor.getClass()).invoke(instructor, valor);
        }

        this.obtenerUsuarioEnFecha(instructor.getUsuario(), fecha);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object getValue(String campo, TipoDato tipo, String val) {
        if (val == null)
            return null;

        return switch (tipo) {
            case STRING -> val;
            case INT -> Integer.parseInt(val);
            case DOUBLE -> Double.parseDouble(val);
            case BOOLEAN -> Boolean.parseBoolean(val);
            case DATE -> LocalDateTime.parse(val);
            case ENTITY -> {
                try {
                   // Get repository bean name
                    String repositoryName = capitalize(campo) + "Repository";

                    // Get repository from Spring context
                    GenericRepository<BaseEntity> repository = (GenericRepository<BaseEntity>) context
                            .getBean(repositoryName);

                    // Find entity by ID
                    yield repository.findById(Long.parseLong(val))
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    capitalize(campo) + " not found with id: " + val));
                } catch (Exception e) {
                    throw new RuntimeException("Error converting to entity: " + e.getMessage(), e);
                }
            }
            case ENUM -> {
                try {
                    // Get enum class from field name
                    String enumClassName = campo.substring(0, 1).toUpperCase() + campo.substring(1);
                    Class<Enum> enumClass = (Class<Enum>) Class.forName(
                            "com.example.demo.entities." + enumClassName);
                    yield Enum.valueOf(enumClass, val);
                } catch (Exception e) {
                    throw new RuntimeException("Error converting to enum: " + e.getMessage(), e);
                }
            }
            default -> throw new IllegalArgumentException("Tipo de dato no soportado: " + tipo);
        };
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
