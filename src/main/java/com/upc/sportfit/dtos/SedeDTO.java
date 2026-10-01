package com.upc.sportfit.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SedeDTO {

    private Integer idSede;
    private String nombre;
    private String distrito;
    private String direccion;
    private String urlUbicacion;
    private String imgSede;
    private UsuarioDTO usuario;
}
