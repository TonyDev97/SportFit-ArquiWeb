package com.upc.sportfit.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MotivoCancelacionDTO {

    private String tipoCancelacion;
    private Long cantidad;

}