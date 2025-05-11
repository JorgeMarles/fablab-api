package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Archivo;

public interface ArchivoRepository extends JpaRepository<Archivo, String> {
    // No additional methods are needed here as the base repository already provides
    // the necessary CRUD operations.
    
}
