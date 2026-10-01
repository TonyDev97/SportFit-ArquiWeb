package com.upc.sportfit.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDeporteSede {

    private String Deporte;
    private String Sede;
    private Integer frecuencia;



}
