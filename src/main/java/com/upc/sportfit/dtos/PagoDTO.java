package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Integer idPago;
    private BigDecimal montoTotal;
    private String metodo;
    private String urlComprobante;
}