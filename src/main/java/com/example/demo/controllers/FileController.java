package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Usuario;
import com.example.demo.services.FileService;
import com.example.demo.utils.Pair;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/{uuid}/")
    public ResponseEntity<Resource> serveFile(@PathVariable String uuid) throws IOException {
        Pair<String,Resource> p = fileService.getFile(uuid);
        // Determine content type
        String contentType = fileService.determineContentType(p.getSecond().getFilename());
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + p.getFirst() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .body(p.getSecond());
        /*
         * return ResponseEntity.ok()
         * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
         * resource.getFilename() + "\"")
         * .body(resource);
         */
    }
    /*
     * @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
     * public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile
     * file) throws Exception {
     * Archivo archivo = fileService.uploadFile(file);
     * return ResponseEntity.ok(archivo.getUrl());
     * }
     */
}
