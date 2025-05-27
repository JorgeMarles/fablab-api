package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.response.CertificadoItemDTO;
import com.example.demo.entities.Archivo;
import com.example.demo.entities.Certificado;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CertificadoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CertificadoService {
    @Autowired
    private CertificadoRepository certificadoRepository;

    @Autowired
    private FileService fileService;

    public void crearCertificado(OfertaFormacion oferta, Instructor instructor, LocalDateTime fecha,
            PlantillaCertificado plantilla) {
        // Crear un nuevo certificado
        Certificado certificado = new Certificado();
        certificado.setFecha(fecha);
        certificado.setInstructor(instructor);
        certificado.setOfertaFormacion(oferta);
        certificado.setPlantilla(plantilla);

        // Guardar el certificado en la base de datos
        certificadoRepository.save(certificado);
    }

    public Certificado obtenerPorId(Long id) {
        return certificadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificado no encontrado."));
    }

    private static final DateTimeFormatter SPANISH_FORMATTER = DateTimeFormatter
            .ofPattern("EEEE, d 'de' MMMM 'de' yyyy 'a las' h:mm:ss a", Locale.forLanguageTag("es-CO"));
    

    public Resource generarCertificado(Long id) throws FileException, IOException {
        Certificado certificado = this.obtenerPorId(id);
        Map<String, String> data = new HashMap<>();
        data.put("{{NOMBRE_INSTRUCTOR}}", certificado.getInstructor().getUsuario().getNombreCompleto());
        data.put("{{NOMBRE_OFERTA}}", certificado.getOfertaFormacion().getNombre());
        data.put("{{FECHA}}", certificado.getFecha().format(SPANISH_FORMATTER));
        data.put("{{ID}}", certificado.getId().toString());
        Archivo archivo = certificado.getPlantilla().getArchivo();
        Resource resource = fileService.getFile(archivo.getUuid()).getSecond();
        log.info("Plantilla {}", resource.getFilename());
        try (InputStream inputStream = new FileInputStream(resource.getFile());
                POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream)) {
            HWPFDocument doc = new HWPFDocument(fileSystem);
            // replace text in doc and save changes
            Range range = doc.getRange();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                log.info("Reemplazando {} por {}", entry.getKey(), entry.getValue());
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.write(out);
            doc.close();
            Resource certificadoFinal = new ByteArrayResource(out.toByteArray());
            return certificadoFinal;
        }

    }

    public List<CertificadoItemDTO> listar(Long id) {
        List<Certificado> certificados = certificadoRepository.findByInstructor_Id(id);
        List<CertificadoItemDTO> certs = new ArrayList<>();
        for (Certificado c : certificados) {
            CertificadoItemDTO dto = new CertificadoItemDTO();
            dto.parseFromEntity(c);
            certs.add(dto);
        }
        return certs;
    }
}
