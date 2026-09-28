package com.upc.sportfit.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "sede_cancha")
public class SedeCancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede_cancha", nullable = false)
    private Integer idSedeCancha;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Size(max = 300)
    @Column(name = "img_cancha", length = 300)
    private String imgCancha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;
    //nombre modificado por rara incosistencia

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cancha", nullable = false)
    private TipoCancha cancha;


}