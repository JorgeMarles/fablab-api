package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
      
}
