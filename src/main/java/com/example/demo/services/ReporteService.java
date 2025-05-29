package com.example.demo.services;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.ReporteCursoDTO;
import com.example.demo.DTO.response.ReporteDTO;
import com.example.demo.DTO.response.ReporteEducacionContinuaDTO;
import com.example.demo.DTO.response.ReporteEducacionContinuaDetalleDTO;
import com.example.demo.DTO.response.ReporteParticipanteDTO;
import com.example.demo.DTO.response.ReportePracticanteDTO;
import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.Inscripcion;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.Participante;
import com.example.demo.entities.TipoBeneficiario;
import com.example.demo.utils.ExcelGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReporteService {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    private OfertaFormacionService ofertaFormacionService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ParticipanteService participanteService;

    @Data
    @AllArgsConstructor
    private class Plantilla {
        private Callable<Resource> generadorArchivo;
        private Callable<List<ReporteDTO>> generadorDatos;
    }

    private Map<String, Plantilla> plantillasMap = Map.of(
            "PLANTILLA_CURSOS",
            new Plantilla(this::generarReporteCursoExcel, this::generarReporteCurso),
            "PLANTILLA_EDUCACION_CONTINUA",
            new Plantilla(this::generarReporteEducacionContinuaExcel, this::generarReporteEducacionContinua),
            "PLANTILLA_PRACTICANTES",
            new Plantilla(this::generarReportePracticantesExcel, this::generarReportePracticantes),
            "PLANTILLA_PARTICIPANTES",
            new Plantilla(this::generarReporteParticipantesExcel, this::generarReporteParticipantes));

    public Resource generarExcel(String nombrePlantilla) throws Exception {
        Plantilla plantilla = plantillasMap.get(nombrePlantilla);
        if (plantilla == null) {
            throw new IllegalArgumentException("No existe una plantilla con el nombre: " + nombrePlantilla);
        }
        return plantilla.generadorArchivo.call();
    }

    public List<ReporteDTO> generarReporte(String nombrePlantilla) throws Exception {
        Plantilla plantilla = plantillasMap.get(nombrePlantilla);
        if (plantilla == null) {
            throw new IllegalArgumentException("No existe una plantilla con el nombre: " + nombrePlantilla);
        }
        return plantilla.generadorDatos.call();
    }

    public List<ReporteDTO> generarReporteCurso() {
        ReporteDTO reporte = new ReporteDTO();
        reporte.setNombre("CURSOS");
        List<ReporteCursoDTO> cursos = this.getReporteCurso();
        for (ReporteCursoDTO curso : cursos) {
            reporte.getDatos().add(curso);
        }
        return List.of(reporte);
    }

    public List<ReporteDTO> generarReporteEducacionContinua() {
        ReporteDTO reporteEducacionContinua = new ReporteDTO();
        reporteEducacionContinua.setNombre("EDUCACION_CONTINUA");
        List<ReporteEducacionContinuaDTO> educacionContinua = this.getReporteEducacionContinua();
        for (ReporteEducacionContinuaDTO educacion : educacionContinua) {
            reporteEducacionContinua.getDatos().add(educacion);
        }
        ReporteDTO detalle = new ReporteDTO();
        detalle.setNombre("DETALLE");
        List<ReporteEducacionContinuaDetalleDTO> detalles = this.getReporteEducacionContinuaDetalle();
        for (ReporteEducacionContinuaDetalleDTO detalleEducacion : detalles) {
            detalle.getDatos().add(detalleEducacion);
        }
        return List.of(reporteEducacionContinua, detalle);
    }

    public List<ReporteCursoDTO> getReporteCurso() {
        List<OfertaFormacion> ofertas = ofertaFormacionService.listarTodosEntidades();
        return ofertas.stream().map(of -> {
            ReporteCursoDTO dto = new ReporteCursoDTO();
            dto.setCodigo_curso(of.getCodigo());
            dto.setNombre_curso(of.getNombre());
            dto.setId_cine_campo_detallado(of.getCine());
            dto.setEs_extension(of.isExtension() ? "S" : "N");
            dto.setActivo(of.getEstado() == EstadoOfertaFormacion.ACTIVA ? "S" : "N");
            return dto;
        }).toList();
    }

    public List<ReporteEducacionContinuaDTO> getReporteEducacionContinua() {
        List<OfertaFormacion> ofertas = ofertaFormacionService.listarTodosEntidades();
        List<ReporteEducacionContinuaDTO> reportes = new LinkedList<>();

        for (OfertaFormacion oferta : ofertas) {
            int cantidad = oferta.getInscripciones().size();
            int cantidadXTB = cantidad / oferta.getTiposBeneficiario().size();
            int sobra = cantidad % oferta.getTiposBeneficiario().size();
            Instructor instructor = oferta.getInstructores().get(0);

            int year = oferta.getFechaInicio().getYear(); // Ajuste para obtener el año correcto
            for (TipoBeneficiario tipo : oferta.getTiposBeneficiario()) {
                ReporteEducacionContinuaDTO reporte = new ReporteEducacionContinuaDTO();
                reporte.setAño(year);
                reporte.setSemestre(oferta.getSemestre());
                reporte.setCodigo_curso(oferta.getCodigo());
                reporte.setNum_horas(oferta.getHoras());
                reporte.setId_tipo_curso_extension(oferta.getTipo().getId());
                reporte.setValor_curso(oferta.getValor());
                reporte.setId_tipo_documento(instructor.getUsuario().getTipoDocumento().getSiglas());
                reporte.setNum_documento(instructor.getUsuario().getDocumento());
                reporte.setId_tipo_benef_extension(tipo.getId());
                reporte.setCantidad_beneficiarios(cantidadXTB + sobra);
                sobra = 0;
                reportes.add(reporte);
            }
        }

        return reportes;
    }

    public List<ReporteEducacionContinuaDetalleDTO> getReporteEducacionContinuaDetalle() {
        List<OfertaFormacion> ofertas = ofertaFormacionService.listarTodosEntidades();
        List<ReporteEducacionContinuaDetalleDTO> reportes = new LinkedList<>();

        for (OfertaFormacion oferta : ofertas) {
            Instructor instructor = oferta.getInstructores().get(0);
            for (Inscripcion inscripcion : oferta.getInscripciones()) {
                ReporteEducacionContinuaDetalleDTO reporte = new ReporteEducacionContinuaDetalleDTO(oferta, inscripcion,
                        instructor);
                reportes.add(reporte);
            }
        }

        return reportes;
    }

    public Resource generarReporteEducacionContinuaExcel() throws Exception {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        int sheetEducacionContinua = excelGenerator.addSheet("EDUCACION_CONTINUA").getSecond();

        excelGenerator.addHeaders(sheetEducacionContinua,
                new String[] { "AÑO", "SEMESTRE", "CODIGO_CURSO", "NUM_HORAS", "ID_TIPO_CURSO_EXTENSION",
                        "VALOR_CURSO", "ID_TIPO_DOCUMENTO", "NUM_DOCUMENTO", "ID_TIPO_BENEF_EXTENSION",
                        "CANTIDAD_BENEFICIARIOS" });

        List<ReporteEducacionContinuaDTO> reportes = this.getReporteEducacionContinua();
        List<List<Object>> data = reportes.stream().map(reporte -> {
            return List.<Object>of(reporte.getAño(), reporte.getSemestre(), reporte.getCodigo_curso(),
                    reporte.getNum_horas(), reporte.getId_tipo_curso_extension(), reporte.getValor_curso(),
                    reporte.getId_tipo_documento(), reporte.getNum_documento(), reporte.getId_tipo_benef_extension(),
                    reporte.getCantidad_beneficiarios());
        }).toList();

        excelGenerator.addData(sheetEducacionContinua, data);

        int sheetDetalle = excelGenerator.addSheet("DETALLE").getSecond();

        excelGenerator.addHeaders(sheetDetalle,
                new String[] { "CODIGO_CURSO", "ID_TIPO_BENEF_EXTENSION", "NOMBRE_DEL_CURSO", "TIPO_DE_CURSO",
                        "FECHA_INICIO_DEL_CURSO", "TIPO_DOCUMENTO_DEL_PARTICIPANTE", "NUMERO_DE_DOCUMENTO",
                        "NOMBRE_Y_APELLIDO_DEL_PARTICIPANTE", "PROGRAMA", "VALOR_DE_CURSO",
                        "NUMERO_DE_HORAS_DEL_CURSO", "DOCENTE_IMPARTIO_EL_CURSO", "TIPO_DOCUMENTO_DOCENTE",
                        "NUMERO_DEL_DOCUMENTO" });

        List<ReporteEducacionContinuaDetalleDTO> detalles = this.getReporteEducacionContinuaDetalle();
        List<List<Object>> data2 = detalles.stream().map(reporte -> {
            return List.<Object>of(reporte.getCodigo_curso(), reporte.getId_tipo_benef_extension(),
                    reporte.getNombre_del_curso(), reporte.getTipo_de_curso(),
                    reporte.getFecha_inicio_del_curso(), reporte.getTipo_de_documento_del_participante(),
                    reporte.getNumero_de_documento(), reporte.getNombre_y_apellido_del_participante(),
                    reporte.getPrograma(), reporte.getValor_de_curso(), reporte.getNumero_de_horas_del_curso(),
                    reporte.getDocente_impartio_el_curso(), reporte.getTipo_documento_docente(),
                    reporte.getNumero_del_documento());
        }).toList();

        excelGenerator.addData(sheetDetalle, data2);

        return excelGenerator.generateExcel();
    }

    public Resource generarReporteCursoExcel() throws Exception {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        excelGenerator.addSheet("CURSO");

        excelGenerator.addHeaders(0,
                new String[] { "CODIGO_CURSO", "NOMBRE_CURSO", "ID_CINE_CAMPO_DETALLADO", "ES_EXTENSION", "ACTIVO" });

        List<ReporteCursoDTO> ofertas = this.getReporteCurso();
        List<List<Object>> data = ofertas.stream().map(oferta -> {
            return List.<Object>of(oferta.getCodigo_curso(), oferta.getNombre_curso(),
                    oferta.getId_cine_campo_detallado(), oferta.getEs_extension(), oferta.getActivo());
        }).toList();

        excelGenerator.addData(0, data);

        return excelGenerator.generateExcel();
    }

    public List<ReportePracticanteDTO> getReportePracticantes() {
        List<ReportePracticanteDTO> practicantes = new LinkedList<>();
        List<Instructor> instructores = instructorService.listarEntidad();
        for (Instructor instructor : instructores) {
            ReportePracticanteDTO dto = new ReportePracticanteDTO(instructor);
            practicantes.add(dto);
        }
        return practicantes;
    }

    public Resource generarReportePracticantesExcel() throws Exception {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        int sheetPracticantes = excelGenerator.addSheet("PRACTICANTES").getSecond();

        excelGenerator.addHeaders(sheetPracticantes,
                new String[] { "AÑO", "SEMESTRE", "ID_TIPO_DOCUMENTO", "NUM_DOCUMENTO", "FECHA_EXPEDICION",
                        "PRIMER_NOMBRE", "SEGUNDO_NOMBRE", "PRIMER_APELLIDO", "SEGUNDO_APELLIDO", "ID_SEXO_BIOLOGICO",
                        "FECHA_NACIMIENTO", "ID_PAIS", "MUNICIPIO_NACIMIENTO", "DIRECCION_RESIDENCIA",
                        "TELEFONO_CONTACTO", "EMAIL_PERSONAL", "ENTIDAD_DONDE_REALIZA_PRACTICA_O_PASANTIA",
                        "MODALIDAD" });

        List<ReportePracticanteDTO> reportes = this.getReportePracticantes();
        List<List<Object>> data = reportes.stream().map(reporte -> {
            return List.<Object>of(reporte.getAño(), reporte.getSemestre(), reporte.getId_tipo_documento(),
                    reporte.getNum_documento(), reporte.getFecha_expedicion(), reporte.getPrimer_nombre(),
                    reporte.getSegundo_nombre(), reporte.getPrimer_apellido(), reporte.getSegundo_apellido(),
                    reporte.getId_sexo_biologico(), reporte.getFecha_nacimiento(), reporte.getId_pais(),
                    reporte.getMunicipio_nacimiento(), reporte.getDireccion_residencia(),
                    reporte.getTelefono_contacto(), reporte.getEmail_personal(),
                    reporte.getEntidad_donde_realiza_practica_o_pasantia(), reporte.getModalidad());
        }).toList();

        excelGenerator.addData(sheetPracticantes, data);

        return excelGenerator.generateExcel();
    }

    public List<ReporteDTO> generarReportePracticantes() {
        ReporteDTO reporte = new ReporteDTO();
        reporte.setNombre("PRACTICANTES");
        List<ReportePracticanteDTO> practicantes = this.getReportePracticantes();
        reporte.getDatos().addAll(practicantes);
        return List.of(reporte);
    }

    public List<ReporteParticipanteDTO> getReporteParticipantes() {
        List<ReporteParticipanteDTO> participantes = new LinkedList<>();
        List<Participante> listaParticipantes = participanteService.listarEntidad();
        for (Participante participante : listaParticipantes) {
            ReporteParticipanteDTO dto = new ReporteParticipanteDTO(participante);
            participantes.add(dto);
        }
        return participantes;
    }

    public List<ReporteDTO> generarReporteParticipantes() {
        ReporteDTO reporte = new ReporteDTO();
        reporte.setNombre("PARTICIPANTES");
        List<ReporteParticipanteDTO> participantes = this.getReporteParticipantes();
        reporte.getDatos().addAll(participantes);
        return List.of(reporte);
    }

    public Resource generarReporteParticipantesExcel() throws Exception {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        int sheetParticipantes = excelGenerator.addSheet("PARTICIPANTES").getSecond();

        excelGenerator.addHeaders(sheetParticipantes,
                new String[] { "ID_TIPO_DOCUMENTO", "NUM_DOCUMENTO", "FECHA_EXPEDICION", "PRIMER_NOMBRE",
                        "SEGUNDO_NOMBRE", "PRIMER_APELLIDO", "SEGUNDO_APELLIDO", "ID_SEXO_BIOLOGICO",
                        "ID_ESTADO_CIVIL", "FECHA_NACIMIENTO", "ID_PAIS", "ID_MUNICIPIO", "TELEFONO_CONTACTO",
                        "EMAIL_PERSONAL", "EMAIL_INSTITUCIONAL", "DIRECCION_INSTITUCIONAL" });

        List<ReporteParticipanteDTO> reportes = this.getReporteParticipantes();
        List<List<Object>> data = reportes.stream().map(reporte -> {
            return List.<Object>of(reporte.getId_tipo_documento(), reporte.getNum_documento(),
                    reporte.getFecha_expedicion(), reporte.getPrimer_nombre(), reporte.getSegundo_nombre(),
                    reporte.getPrimer_apellido(), reporte.getSegundo_apellido(), reporte.getId_sexo_biologico(),
                    reporte.getId_estado_civil(), reporte.getFecha_nacimiento(), reporte.getId_pais(),
                    reporte.getId_municipio(), reporte.getTelefono_contacto(), reporte.getEmail_personal(),
                    reporte.getEmail_institucional(), reporte.getDireccion_institucional());
        }).toList();

        excelGenerator.addData(sheetParticipantes, data);

        return excelGenerator.generateExcel();
    }

}
