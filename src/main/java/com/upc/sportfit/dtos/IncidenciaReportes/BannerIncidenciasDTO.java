package com.upc.sportfit.dtos.IncidenciaReportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BannerIncidenciasDTO {
    private Long totalHistorico;
    private Long pendientesMes;
    private Long atendidasMes;
}
