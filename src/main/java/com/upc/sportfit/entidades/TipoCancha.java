package com.upc.sportfit.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoCancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cancha", nullable = false)
    private Integer idTipoCancha;

    @Size(max = 20)
    @NotNull
    @Column(name = "deporte", nullable = false, length = 20)
    private String deporte;

    @Size(max = 250)
    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "aforo")
    private Long aforo;

    // Fecha como campo simple
    @Column(name = "f_creacion")
    private LocalDate fechaCreacion;
}