package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.GenericEntityService;

@RestController
@RequestMapping("/api/entities")
public class GenericEntityController {

    @Autowired
    private GenericEntityService genericEntityService;

    @PostMapping("/{entityName}/")
    public ResponseEntity<?> createEntity(@PathVariable String entityName, @RequestBody Map<String, Object> entity) throws Exception {
        return ResponseEntity.created(null).body(genericEntityService.createEntity(entityName, entity));
    }
}
