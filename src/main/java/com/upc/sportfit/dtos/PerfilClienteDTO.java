package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class PerfilClienteDTO {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    private Boolean activo;
    private String imgUsuario;
    private Instant fCreacion;

}
