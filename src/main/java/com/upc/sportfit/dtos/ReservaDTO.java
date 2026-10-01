package com.upc.sportfit.dtos;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.entidades.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Integer idReserva;
    private LocalDate fReserva;
    private LocalTime hInicio;
    private LocalTime hFin;
    private String estado;
    private String creadoPor;
    private Instant fCreacion;
    private String modificadoPor;
    private Instant fModificacion;
    private UsuarioDTO usuario;
    private SedeCanchaDTO sedeCancha;



}
