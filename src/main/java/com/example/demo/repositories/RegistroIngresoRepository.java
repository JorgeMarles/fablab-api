package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Motivo;
import com.example.demo.entities.RegistroIngreso;

public interface RegistroIngresoRepository extends JpaRepository<RegistroIngreso, Long> {
    List<RegistroIngreso> findByUsuario_Id(Long idUsuario);
    List<RegistroIngreso> findByMotivo(Motivo motivo);
}
