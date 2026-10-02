package com.upc.sportfit.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("fReserva")
    private LocalDate fReserva;
    @JsonProperty("fInicio")
    private LocalTime hInicio;
    @JsonProperty("fFin")
    private LocalTime hFin;
    private String estado;
    private String creadoPor;
    @JsonProperty("fCreacion")
    private Instant fCreacion;
    private String modificadoPor;
    @JsonProperty("fModificacion")
    private Instant fModificacion;
    private UsuarioDTO usuario;
    private SedeCanchaDTO sedeCancha;



}
