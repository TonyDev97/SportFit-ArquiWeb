package com.upc.sportfit.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TipoCanchaDTO {

    private Integer idTipoCancha;
    private String deporte;
    private String descripcion;
    private Long aforo;
    private LocalDate fechaCreacion;
}
