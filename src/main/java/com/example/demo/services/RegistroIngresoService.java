package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.RegistroIngresoDTO;
import com.example.demo.DTO.response.IngresoFablabItemDTO;
import com.example.demo.entities.Cargo;
import com.example.demo.entities.Institucion;
import com.example.demo.entities.Motivo;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.ProgramaAcademico;
import com.example.demo.entities.RegistroIngreso;
import com.example.demo.entities.Sala;
import com.example.demo.entities.Semillero;
import com.example.demo.entities.TipoInstitucion;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.RegistroIngresoRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroIngresoService {
	@Autowired
	private RegistroIngresoRepository registroIngresoRepository;

	@Autowired
	private OfertaFormacionService ofertaFormacionService;

	@Autowired
	private InstitucionService institucionService;

	@Autowired
	private ProgramaAcademicoService programaAcademicoService;

	@Autowired
	private CargoService cargoService;

	@Autowired
	private SalaService salaService;

	@Autowired
	private SemilleroService semilleroService;

	@Autowired
	private UsuarioService usuarioService;

	public List<IngresoFablabItemDTO> listar() {
		return registroIngresoRepository.findAll().stream().map(registro -> {
			IngresoFablabItemDTO dto = new IngresoFablabItemDTO();
			dto.parseFromEntity(registro);
			return dto;
		}).toList();
	}

	@Transactional
	public IngresoFablabItemDTO crear(RegistroIngresoDTO registroIngresoDTO, Long idUsuario) {
		RegistroIngreso registroIngreso = new RegistroIngreso();

		Usuario usuario = usuarioService.obtenerPorIdEntidad(idUsuario);
		if (registroIngresoDTO.getMotivo() != null) {
			registroIngreso.setMotivo(registroIngresoDTO.getMotivo());
		} else {
			throw new IllegalArgumentException("El motivo es obligatorio.");
		}
		registroIngreso.setUsuario(usuario);
		if (registroIngresoDTO.getMotivo() == Motivo.CURSO) {
			addOfertaFormacion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
		} else if (registroIngresoDTO.getMotivo() == Motivo.CURSO_A_COLEGIO) {
			addOfertaFormacion(registroIngreso, registroIngresoDTO, TipoInstitucion.COLEGIO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.STEAM_SCHOOL
				|| registroIngresoDTO.getMotivo() == Motivo.STEAM_YOUNG) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.COLEGIO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.CLASE) {
			addSala(registroIngreso, registroIngresoDTO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.SOCIALIZACION) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
		} else if (registroIngresoDTO.getMotivo() == Motivo.SOCIALIZACION_A_COLEGIO) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.COLEGIO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.SEMILLERO) {
			addSemillero(registroIngreso, registroIngresoDTO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.GRABACION_CARRERA) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
		} else if (registroIngresoDTO.getMotivo() == Motivo.GRABACION_SEMILLERO) {
			addSemillero(registroIngreso, registroIngresoDTO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.GRABACION_EXTERNO) {
			addAsociacion(registroIngreso, registroIngresoDTO);
		} else if (registroIngresoDTO.getMotivo() == Motivo.SUSTENTACION_PROYECTO_GRADO) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
		} else if (registroIngresoDTO.getMotivo() == Motivo.PRACTICANTE) {
			addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
		} else if (registroIngresoDTO.getMotivo() == Motivo.INFORME_FINAL) {
			addCargo(registroIngreso, registroIngresoDTO);
		} else {
			throw new IllegalArgumentException("El motivo no es válido.");
		}

		registroIngreso.setTiempo(LocalDateTime.now());

		registroIngreso = registroIngresoRepository.save(registroIngreso);
		IngresoFablabItemDTO registroIngresoResponse = new IngresoFablabItemDTO();
		registroIngresoResponse.parseFromEntity(registroIngreso);

		return registroIngresoResponse;
	}

	private void addAsociacion(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getAsociacion() == null) {
			throw new IllegalArgumentException("La asociacion es obligatoria.");
		}
		registroIngreso.setAsociacion(registroIngresoDTO.getAsociacion());
		addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
	}

	private void addSala(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getId_sala() == null) {
			throw new IllegalArgumentException("El id_sala es obligatorio.");
		}
		Optional<Sala> sala = salaService.obtenerPorIdEntidad(registroIngresoDTO.getId_sala());
		if (sala.isPresent()) {
			registroIngreso.setSala(sala.get());
		} else {
			throw new ResourceNotFoundException("No existe sala con ese id.");
		}
		addCodigoCarrera(registroIngreso, registroIngresoDTO);
	}

	private void addCodigoCarrera(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getCodigo() == null) {
			throw new IllegalArgumentException("El codigo es obligatorio.");
		}
		String codigo = registroIngresoDTO.getCodigo();
		if (codigo.length() < 3) {
			throw new IllegalArgumentException("El codigo debe tener al menos 3 caracteres.");
		}
		registroIngreso.setCodigoCarrera(codigo.substring(0, 3));
		addMateria(registroIngreso, registroIngresoDTO);;
	}

	private void addOfertaFormacion(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO, TipoInstitucion tipo) {
		if (registroIngresoDTO.getId_oferta_formacion() == null) {
			throw new IllegalArgumentException("El id_oferta_formacion es obligatorio.");
		}
		Optional<OfertaFormacion> oferta = ofertaFormacionService
				.obtenerPorIdEntidad(registroIngresoDTO.getId_oferta_formacion());
		if (oferta.isPresent()) {
			if(oferta.get().getInstituciones().stream().anyMatch(institucion -> institucion.getTipoInstitucion() != tipo)) {
				throw new IllegalArgumentException("La oferta de formación no es del tipo esperado.");
			}
			registroIngreso.setOfertaFormacion(oferta.get());
		} else {
			throw new ResourceNotFoundException("No existe oferta de formación con ese id.");
		}
		addInstitucion(registroIngreso, registroIngresoDTO, tipo);
	}

	private void addInstitucion(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO, TipoInstitucion tipo) {
		if (registroIngresoDTO.getId_institucion() == null || registroIngresoDTO.getNombre_institucion() == null) {
			throw new IllegalArgumentException("El id_institucion o el nombre_institucion son obligatorios.");
		}
		if (registroIngresoDTO.getId_institucion() == null) {
			registroIngreso.setInstitucionNombre(registroIngresoDTO.getNombre_institucion());
			return;
		}
		Optional<Institucion> institucion = institucionService
				.obtenerPorIdEntidad(registroIngresoDTO.getId_institucion());
		if (institucion.isPresent()) {
			registroIngreso.setInstitucion(institucion.get());
			registroIngreso.setInstitucionNombre(registroIngresoDTO.getNombre_institucion());
		} else {
			throw new ResourceNotFoundException("No existe institución con ese id.");
		}

		if (tipo == TipoInstitucion.UNIVERSIDAD) {
			addProgramaAcademico(registroIngreso, registroIngresoDTO);
		} else {
			addCargo(registroIngreso, registroIngresoDTO);
		}
	}

	private void addProgramaAcademico(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getId_programa_academico() == null) {
			throw new IllegalArgumentException("El id_programa_academico es obligatorio.");
		}
		Optional<ProgramaAcademico> programaAcademico = programaAcademicoService
				.obtenerPorIdEntidad(registroIngresoDTO.getId_programa_academico());
		if (programaAcademico.isPresent()) {
			registroIngreso.setProgramaAcademico(programaAcademico.get());
		} else {
			throw new ResourceNotFoundException("No existe programa académico con ese id.");
		}
		addCodigo(registroIngreso, registroIngresoDTO);
	}

	private void addCargo(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getId_cargo() == null) {
			throw new IllegalArgumentException("El id_cargo es obligatorio.");
		}
		Optional<Cargo> cargo = cargoService.obtenerPorIdEntidad(registroIngresoDTO.getId_cargo());
		if (cargo.isPresent()) {
			registroIngreso.setCargo(cargo.get());
		} else {
			throw new ResourceNotFoundException("No existe cargo con ese id.");
		}
	}

	private void addCodigo(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getCodigo() == null) {
			throw new IllegalArgumentException("El codigo es obligatorio.");
		}
		registroIngreso.setCodigo(registroIngresoDTO.getCodigo());
		addCargo(registroIngreso, registroIngresoDTO);
	}

	private void addMateria(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getMateria() == null) {
			throw new IllegalArgumentException("La materia es obligatoria.");
		}
		registroIngreso.setMateria(registroIngresoDTO.getMateria());
		addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
	}

	private void addSemillero(RegistroIngreso registroIngreso, RegistroIngresoDTO registroIngresoDTO) {
		if (registroIngresoDTO.getId_semillero() == null || registroIngresoDTO.getNombre_semillero() == null) {
			throw new IllegalArgumentException("El id_semillero o el nombre_semillero son obligatorios.");
		}
		if (registroIngresoDTO.getId_semillero() == null) {
			registroIngreso.setSemilleroNombre(registroIngresoDTO.getNombre_semillero());
			registroIngreso.setSemilleroSiglas(getSiglas(registroIngresoDTO.getNombre_semillero()));
			return;
		}
		Optional<Semillero> semillero = semilleroService
				.obtenerPorIdEntidad(registroIngresoDTO.getId_semillero());
		if (semillero.isPresent()) {
			registroIngreso.setSemillero(semillero.get());
			registroIngreso.setSemilleroNombre(semillero.get().getNombre());
			registroIngreso.setSemilleroSiglas(semillero.get().getSiglas());
		} else {
			throw new ResourceNotFoundException("No existe semillero con ese id.");
		}

		addInstitucion(registroIngreso, registroIngresoDTO, TipoInstitucion.UNIVERSIDAD);
	}

	private String getSiglas(String str) {
		if (!str.contains(" ")) {
			return str.toUpperCase();
		}
		String[] words = str.split(" ");
		StringBuilder siglas = new StringBuilder();
		for (String word : words) {
			if (!word.isEmpty()) {
				siglas.append(word.charAt(0));
			}
		}
		return siglas.toString().toUpperCase();
	}

}