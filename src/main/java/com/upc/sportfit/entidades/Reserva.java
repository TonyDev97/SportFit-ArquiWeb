package com.upc.sportfit.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "f_reserva", nullable = false)
    private LocalDate fReserva;

    @NotNull
    @Column(name = "h_inicio", nullable = false)
    private LocalTime hInicio;

    @NotNull
    @Column(name = "h_fin", nullable = false)
    private LocalTime hFin;

    @Size(max = 25)
    @NotNull
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;

    @Size(max = 100)
    @NotNull
    @Column(name = "creado_por", nullable = false, length = 100)
    private String creadoPor;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "f_creacion", nullable = false)
    private Instant fCreacion;

    @Size(max = 100)
    @Column(name = "modificado_por", length = 100)
    private String modificadoPor;

    @Column(name = "f_modificacion")
    private Instant fModificacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede_cancha", nullable = false)
    private SedeCancha idSedeCancha;


}