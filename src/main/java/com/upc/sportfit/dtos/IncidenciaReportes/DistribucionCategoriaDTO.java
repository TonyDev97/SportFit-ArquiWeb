package com.upc.sportfit.dtos.IncidenciaReportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DistribucionCategoriaDTO {
    private String categoria;
    private Long cantidad;
    private Double porcentaje;
}
