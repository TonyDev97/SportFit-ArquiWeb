package com.upc.sportfit.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BannerPagoReporte {
    private BigDecimal recaudacionTotal;
    private BigDecimal promedioDiario;
    private BigDecimal diaPico;
    private Long pagosRealizados;
}
