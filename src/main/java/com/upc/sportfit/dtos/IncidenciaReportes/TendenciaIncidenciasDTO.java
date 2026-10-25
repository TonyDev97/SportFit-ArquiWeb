package com.upc.sportfit.dtos.IncidenciaReportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TendenciaIncidenciasDTO {
    private String mes;
    private Long atendidas;
    private Long pendientes;
}
