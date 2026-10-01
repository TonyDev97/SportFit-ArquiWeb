package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaDTO {

    private Integer idIncidencia;
    private String tipo;
    private String asunto;
    private String descripcion;
    private String estado;
    private String respuestaAdmin;
    private Instant fCreacion;
    private Instant fRespuesta;
    private UsuarioDTO usuario;
}