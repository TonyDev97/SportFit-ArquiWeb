package com.upc.sportfit.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaHorarioDTO {
    private Integer idReserva;
    private Integer idSedeCancha;
    private BigDecimal precio;
    private Long aforo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
