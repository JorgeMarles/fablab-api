package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.MovimientoCreacionDTO;
import com.example.demo.DTO.response.MovimientoDTO;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.Movimiento;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.MovimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private InstructorService instructorService;

    @Transactional
    public void guardarMovimiento(MovimientoCreacionDTO dto) {

        Instructor instructor = instructorService.obtenerPorIdEntidad(dto.getId_instructor()).orElseThrow(
                () -> new ResourceNotFoundException("No existe un instructor con el id: " + dto.getId_instructor()));

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dto.getFecha());
        movimiento.setTipoMovimiento(dto.getTipo());
        movimiento.setInstructor(instructor);
        
        movimientoRepository.save(movimiento);
    }

    @Transactional
    public List<MovimientoDTO> listar() {
        return movimientoRepository.findAll().stream()
                .map(movimiento -> {
                    MovimientoDTO dto = new MovimientoDTO();
                    dto.parseFromEntity(movimiento);
                    return dto;
                }).toList();
    }
}
