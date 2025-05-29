package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Usuario;
import com.example.demo.services.FileService;
import com.example.demo.services.ReporteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reportes")
@Slf4j
public class ReporteController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/excel/")
    public ResponseEntity<Resource> getReporteExcel(@RequestParam(name = "plantilla") String plantilla)
            throws Exception {
        log.info("Generating Excel report with template: {}", plantilla);
        Resource p = reporteService.generarExcel(plantilla);
        // Determine content type
        String contentType = fileService.determineContentType(".xlsx");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            log.info("User is anonymous {} ", principal);
        } else if (principal instanceof Usuario) {
            log.info("User is authenticated {} ", ((Usuario) principal).toString());
        } else {
            log.warn("Unknown principal type: {}", principal.getClass().getName());
        }

        // Build response with inline disposition (display in browser)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"Reporte-" + System.currentTimeMillis() + ".xlsx\"")
                .header(HttpHeaders.CACHE_CONTROL, "max-age=1")
                .body(p);
    }

    @GetMapping("/")
    public ResponseEntity<List<?>> getReporte(@RequestParam(name = "plantilla") String plantilla) throws Exception {
        log.info("Generating Excel report with template: {}", plantilla);
        List<?> reportes = reporteService.generarReporte(plantilla);

        // Build response with inline disposition (display in browser)
        return ResponseEntity.ok().body(reportes);
    }
}
