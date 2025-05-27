package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.DTO.request.OfertaCreacionDTO;
import com.example.demo.DTO.response.CategoriaDTO;
import com.example.demo.DTO.response.OfertaDetalleDTO;
import com.example.demo.DTO.response.OfertaItemDTO;
import com.example.demo.DTO.response.TipoBeneficiarioDTO;
import com.example.demo.DTO.response.TipoOfertaDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.services.CategoriaOfertaService;
import com.example.demo.services.OfertaFormacionService;
import com.example.demo.services.TipoBeneficiarioService;
import com.example.demo.services.TipoOfertaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ofertas")
@Slf4j
public class OfertaFormacionController {

    @Autowired
    private OfertaFormacionService ofertaFormacionService;

    @Autowired
    private TipoOfertaService tipoOfertaService;

    @Autowired
    private CategoriaOfertaService categoriaOfertaService;

    @Autowired
    private TipoBeneficiarioService tipoBeneficiarioService;

    @PostMapping("/")
    private ResponseEntity<OfertaDetalleDTO> crearEvidencia(@ModelAttribute OfertaCreacionDTO ofertaFormacionDto,
            @RequestParam("file") MultipartFile file) throws Exception {
        ofertaFormacionDto.setPieza_grafica(file);
        OfertaDetalleDTO creada = ofertaFormacionService.crearRetDto(ofertaFormacionDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @PostMapping("/{id}/inscribir/")
    private ResponseEntity<String> inscribir(@PathVariable(name = "id") Long idOferta) throws Exception {
        Long id = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ofertaFormacionService.inscribir(id, idOferta);
        return ResponseEntity.ok("Inscripción exitosa");
    }

    @PostMapping("/{id}/inscribir-participante/{participanteId}/")
    private ResponseEntity<String> inscribirParticipante(@PathVariable(name = "id") Long idOferta, @PathVariable(name = "participanteId") Long participanteId) throws Exception {
        ofertaFormacionService.inscribir(participanteId, idOferta);
        return ResponseEntity.ok("Inscripción exitosa");
    }

    @DeleteMapping("/{id}/desinscribir-participante/{participanteId}/")
    private ResponseEntity<String> desinscribirParticipante(@PathVariable(name = "id") Long idOferta, @PathVariable(name = "participanteId") Long participanteId) throws Exception {
        ofertaFormacionService.desinscribir(participanteId, idOferta);
        return ResponseEntity.ok("Desinscripción exitosa");
    }

    @PostMapping("/{id}/finalizar/{idPlantilla}/")
    private ResponseEntity<String> finalizarOferta(@PathVariable(name = "id") Long idOferta,
            @PathVariable(name = "idPlantilla") Long idPlantilla)
            throws Exception {
        ofertaFormacionService.finalizar(idOferta, idPlantilla);
        return ResponseEntity.ok("Oferta finalizada");
    }

    @PutMapping("/{id}/")
    private ResponseEntity<OfertaDetalleDTO> editar(@PathVariable(name = "id") Long idOferta,
            @ModelAttribute OfertaCreacionDTO ofertaFormacionDto,
            @RequestParam(name = "file", required = false) MultipartFile file)
            throws Exception {
        if (file != null)
            ofertaFormacionDto.setPieza_grafica(file);
        OfertaDetalleDTO editada = new OfertaDetalleDTO();
        editada.parseFromEntity(ofertaFormacionService.editar(idOferta, ofertaFormacionDto));
        return ResponseEntity.ok(editada);
    }

    @PutMapping("/{id}/switch-estado/")
    private ResponseEntity<OfertaDetalleDTO> editar(@PathVariable(name = "id") Long idOferta)
            throws Exception {
        OfertaDetalleDTO editada = new OfertaDetalleDTO();
        editada.parseFromEntity(ofertaFormacionService.switchEstado(idOferta));
        return ResponseEntity.ok(editada);
    }

    @GetMapping("/")
    // @Preauthorize admin
    public ResponseEntity<List<OfertaItemDTO>> listarTodos(
            @RequestParam(value = "categoria_id", required = false) Long categoriaId) {
        if (categoriaId != null) {
            return ResponseEntity.ok().body(ofertaFormacionService.listarPorCategoria(categoriaId));
        }
        return ResponseEntity.ok().body(ofertaFormacionService.listarTodos());
    }

    @GetMapping("/activas/")
    public ResponseEntity<List<OfertaItemDTO>> listarActivas() {
        return ResponseEntity.ok().body(ofertaFormacionService.listarActivas());
    }

    @GetMapping("/instructor/")
    // preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarInstructor() {
        Long id = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(ofertaFormacionService.listarInstructor(id));
    }

    @GetMapping("/participante/")
    // preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarParticipante() {
        Long id = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(ofertaFormacionService.listarParticipante(id));
    }

    @GetMapping("/{id}/")
    public ResponseEntity<OfertaDetalleDTO> detalle(@PathVariable(name = "id") Long idOferta) {
        return ResponseEntity.ok().body(ofertaFormacionService.obtenerPorIdDetalle(idOferta));
    }

    @GetMapping("/tipos-oferta/")
    public ResponseEntity<List<TipoOfertaDTO>> listarTiposOferta() {
        return ResponseEntity.ok().body(tipoOfertaService.listar());
    }

    @GetMapping("/categorias/")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok().body(categoriaOfertaService.listar());
    }

    @GetMapping("/tipos-beneficiario/")
    public ResponseEntity<List<TipoBeneficiarioDTO>> listarTiposBeneficiario() {
        return ResponseEntity.ok().body(tipoBeneficiarioService.listar());
    }
}
