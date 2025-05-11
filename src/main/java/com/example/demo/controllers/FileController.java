package com.example.demo.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Archivo;
import com.example.demo.services.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @GetMapping("/{uuid}/")
    public ResponseEntity<Resource> serveFile(@PathVariable String uuid) throws IOException {
        Resource resource = fileService.getFile(uuid);
        // Determine content type
        String contentType = fileService.determineContentType(resource.getFilename());

        // Build response with inline disposition (display in browser)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .body(resource);
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
