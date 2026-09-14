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

    @Size(max = 250)
    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Size(max = 20)
    @NotNull
    @Column(name = "deporte", nullable = false, length = 20)
    private String deporte;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancha", nullable = false)
    private Integer id;

    private Long id_tipo_cancha;

    private String nombre;

    private Long aforo;

    private LocalDate f_creacion;
}