package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudReservaDTO {

    private ReservaDTO reserva;
    private List<PagoDTO> pagos;
}