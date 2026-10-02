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
public class DistribucionPagoDTO {

    private String metodoPago;
    private BigDecimal totalRecaudado;
    private Long cantidadTransacciones;

}