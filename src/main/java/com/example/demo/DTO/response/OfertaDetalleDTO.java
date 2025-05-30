package com.example.demo.DTO.response;

import java.util.LinkedList;
import java.util.List;

import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.Sesion;
import com.example.demo.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaDetalleDTO implements IPersonalizableResponseDTO<OfertaFormacion> {

	private Long id;
	private String nombre;
	private String codigo;
	private Integer cine;
	private boolean extension;
	private EstadoOfertaFormacion estado;
	private String fecha_inicio;
	private String fecha_fin;
	private Integer horas;
	private TipoOfertaDTO tipo_oferta;
	private CategoriaDTO categoria;
	private List<TipoBeneficiarioDTO> tipos_beneficiario;
	private Integer semestre;
	private Integer valor;
	private String pieza_grafica;
	private Integer cupo_maximo;
	private List<InstitucionDTO> instituciones;
	private List<SesionItemDTO> sesiones;
	private List<InscritoDTO> inscritos;
	private List<String> roles = new LinkedList<>();

	@Override
	public void parseFromEntity(OfertaFormacion entity) {
		this.id = entity.getId();
		this.nombre = entity.getNombre();
		this.codigo = entity.getCodigo();
		this.cine = entity.getCine();
		this.extension = entity.isExtension();
		this.estado = entity.getEstado();
		this.fecha_inicio = entity.getFechaInicio().toString();
		this.fecha_fin = entity.getFechaFin().toString();
		this.horas = entity.getHoras();
		this.cupo_maximo = entity.getCupoMaximo();

		this.tipo_oferta = new TipoOfertaDTO();
		this.tipo_oferta.parseFromEntity(entity.getTipo());

		this.categoria = new CategoriaDTO();
		this.categoria.parseFromEntity(entity.getCategoria());

		this.tipos_beneficiario = entity.getTiposBeneficiario().stream()
				.map(tipo -> {
					TipoBeneficiarioDTO tipoDTO = new TipoBeneficiarioDTO();
					tipoDTO.parseFromEntity(tipo);
					return tipoDTO;
				})
				.toList();

		this.semestre = entity.getSemestre();
		this.valor = entity.getValor();
		this.pieza_grafica = entity.getPiezaGrafica().getUrl();

		this.instituciones = entity.getInstituciones().stream()
				.map(institucion -> {
					InstitucionDTO institucionDTO = new InstitucionDTO();
					institucionDTO.parseFromEntity(institucion);
					return institucionDTO;
				})
				.toList();
		this.sesiones = entity.getSesiones().stream()
				.map(sesion -> {
					SesionItemDTO sesionDTO = new SesionItemDTO();
					sesionDTO.parseFromEntity(sesion);
					return sesionDTO;
				})
				.toList();

		this.inscritos = entity.getInscripciones().stream()
				.map(inscrito -> {
					InscritoDTO inscritoDTO = new InscritoDTO();
					inscritoDTO.parseFromEntity(inscrito);
					return inscritoDTO;
				})
				.toList();
	}

	@Override
	public void personalizeFromEntity(OfertaFormacion entity, Usuario user) {
		if (user != null && user.getHasPersonalData()) {
			if(user.getAdministrador() != null) {
				this.roles.add("ADMINISTRADOR");
			}
			if(user.getInstructor() != null) {
				if(entity.getInstructores().stream()
						.anyMatch(instructor -> instructor.getId().equals(user.getInstructor().getId()))) {
					this.roles.add("INSTRUCTOR");
				}
			}
			if(user.getParticipante() != null) {
				if(entity.getInscripciones().stream()
						.anyMatch(inscripcion -> inscripcion.getParticipante().getId().equals(user.getParticipante().getId()))) {
					this.roles.add("PARTICIPANTE");
				}
			}
		}
		this.parseFromEntity(entity);
		for(int i = 0; i < this.sesiones.size(); i++) {
			SesionItemDTO sesion = this.sesiones.get(i);
			Sesion entitySesion = entity.getSesiones().get(i);
			sesion.personalizeFromEntity(entitySesion, user);
		}
	}

}
