package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime inicio;
    @ManyToOne
    @JoinColumn(name = "id_oferta_formacion")
    private OfertaFormacion ofertaFormacion;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @OneToMany(mappedBy = "sesion")
    private List<SesionEvidencia> sesionEvidencias;
}
