package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiReservaDTO {
    private Integer idReserva;
    private Integer idSede;          // sirve para filtrar por sede
    private Integer idTipoCancha;    // sirve para filtrar por deporte
    private String deporte;
    private Integer idSedeCancha;    // es lo que se muestra como "Id Cancha"
    private String sedeNombre;
    private LocalDate fReserva;
    private LocalTime hInicio;
    private LocalTime hFin;
    private BigDecimal montoTotal;
    private String metodo;
    private String estado;
}