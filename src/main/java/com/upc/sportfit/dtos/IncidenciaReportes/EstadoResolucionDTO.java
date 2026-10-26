package com.upc.sportfit.dtos.IncidenciaReportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoResolucionDTO {
    private Long atendidas;
    private Long pendientes;
    private Double porcentajeAtendidas;
    private Double porcentajePendientes;
}
