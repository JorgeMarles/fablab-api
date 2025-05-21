package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Archivo;
import com.example.demo.entities.Certificado;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.OfertaFormacion;
import com.example.demo.entities.PlantillaCertificado;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CertificadoRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

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

    public Resource generarCertificado(Long id) throws FileException, IOException {
        Certificado certificado = this.obtenerPorId(id);
        Map<String, String> data = new HashMap<>();
        data.put("{{NOMBRE_INSTRUCTOR}}", certificado.getInstructor().getUsuario().getNombreCompleto());
        data.put("{{NOMBRE_OFERTA}}", certificado.getOfertaFormacion().getNombre());
        data.put("{{FECHA}}", certificado.getFecha().toString());
        data.put("{{ID}}", certificado.getId().toString());
        Archivo archivo = certificado.getPlantilla().getArchivo();
        Resource resource = fileService.getFile(archivo.getUuid()).getSecond();
        log.info("Plantilla {}", resource.getFilename());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(out));
        PdfDocument plantilla = new PdfDocument(new PdfReader(resource.getFile()));
        

        Document document = new Document(pdf);
        plantilla.copyPagesTo(1, plantilla.getNumberOfPages(), pdf);
        log.info("Paginas {}", plantilla.getNumberOfPages());
        for (int page = 1; page <= plantilla.getNumberOfPages(); page++) {
            String text = PdfTextExtractor.getTextFromPage(plantilla.getPage(page));
            if(text != null){
                log.info("Texto extraído de la página {}: {}", page, text);
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    text = text.replace(entry.getKey().toUpperCase(), entry.getValue());
                    text = text.replace(entry.getKey().toLowerCase(), entry.getValue());
                    log.info("Reemplazando {} por {}", entry.getKey(), entry.getValue());
                }
                document.add(new Paragraph(text));
            } else {
                log.warn("No se pudo extraer texto de la página {}", page);
            }
        }

        document.close();
        plantilla.close();
        Resource certificadoFinal = new ByteArrayResource(out.toByteArray());
        return certificadoFinal;
    }
}
