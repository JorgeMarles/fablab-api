package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.example.demo.services.CategoriaOfertaService;
import com.example.demo.services.OfertaFormacionService;
import com.example.demo.services.TipoBeneficiarioService;
import com.example.demo.services.TipoOfertaService;

@RestController
@RequestMapping("/ofertas")
public class OfertaFormacionController {

    Logger logger = LoggerFactory.getLogger(OfertaFormacionController.class);

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

    @PostMapping("/{id}/finalizar/{idPlantilla}/")
    private ResponseEntity<String> finalizarOferta(@PathVariable(name = "id") Long idOferta, @RequestBody Long idPlantilla)
            throws Exception {
        ofertaFormacionService.finalizar(idOferta, idPlantilla);
        return ResponseEntity.ok("Oferta finalizada");
    }

    @GetMapping("/")
    // @Preauthorize admin
    public ResponseEntity<List<OfertaItemDTO>> listarTodos() {
        return ResponseEntity.ok().body(ofertaFormacionService.listarTodos());
    }

    @GetMapping("/instructor/")
    // preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarInstructor() {
        // TODO: Sacar del userdetails del authorization
        Long id = 3L;
        return ResponseEntity.ok().body(ofertaFormacionService.listarInstructor(id));
    }

    @GetMapping("/participante/")
    // preauth instructor
    public ResponseEntity<List<OfertaItemDTO>> listarParticipante() {
        // TODO: Sacar del userdetails del authorization
        Long id = 3L;
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
