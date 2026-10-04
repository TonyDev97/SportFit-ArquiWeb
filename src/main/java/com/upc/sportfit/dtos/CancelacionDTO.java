package com.upc.sportfit.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelacionDTO {
    private Integer idCancelacion;
    private String tipoCancelacion;
    private Instant fCancelacion;
    private ReservaDTO reserva;
}
