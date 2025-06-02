package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.EvidenciaCreacionDTO;
import com.example.demo.DTO.response.EvidenciaDTO;
import com.example.demo.entities.Archivo;
import com.example.demo.entities.Evidencia;
import com.example.demo.entities.Instructor;
import com.example.demo.entities.Sesion;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.EvidenciaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvidenciaService {
    
    @Autowired
    private EvidenciaRepository evidenciaRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private InstructorService instructorService;

    private static final int MAX_EVIDENCIAS_POR_SESION = 5;

    public List<EvidenciaDTO> listarPorSesionId(Long sesionId) {
        return evidenciaRepository.findBySesion_Id(sesionId).stream().map(evidencia -> {
            EvidenciaDTO dto = new EvidenciaDTO();
            dto.parseFromEntity(evidencia);
            return dto;
        }).toList();
    }

    @Transactional
    public void eliminar(Long id) throws IOException {
        Evidencia evidencia = evidenciaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró la evidencia con id: " + id));
        evidenciaRepository.delete(evidencia);
        fileService.deleteFile(evidencia.getArchivo().getUuid());
    }

    @Transactional
    public EvidenciaDTO crear(EvidenciaCreacionDTO dto) throws FileException, IOException {
        Evidencia evidencia = new Evidencia();

        Sesion sesion = sesionService.obtenerPorIdEntidad(dto.getId_sesion()).orElseThrow(() -> new ResourceNotFoundException("No se encontró la sesión con id: " + dto.getId_sesion()));

        Instructor instructor = instructorService.obtenerPorIdEntidad(dto.getId_instructor()).orElseThrow(() -> new ResourceNotFoundException("No se encontró el instructor con id: " + dto.getId_instructor()));

        if(!sesion.getInstructores().contains(instructor)) {
            throw new ResourceNotFoundException("El instructor no está asignado a la sesión");
        }

        if(sesion.getEvidencias().size() >= MAX_EVIDENCIAS_POR_SESION) {
            throw new ResourceNotFoundException("La sesión ya tiene el máximo de evidencias permitidas");

        }

        Archivo archivo = fileService.uploadFile(dto.getArchivo());

        evidencia.setArchivo(archivo);
        evidencia.setSesion(sesion);
        evidencia.setInstructor(instructor);
        evidencia.setNombre(dto.getNombre());

        EvidenciaDTO evidenciaDTO = new EvidenciaDTO();
        evidenciaDTO.parseFromEntity(evidenciaRepository.save(evidencia));

        return evidenciaDTO;
    }

}
