package com.upc.sportfit.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaClienteDTO {

    private String clienteId;
    private String nombreCliente;
    private LocalDate ultimaReserva;
    private Long totalReservas;

}