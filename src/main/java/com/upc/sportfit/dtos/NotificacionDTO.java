package com.upc.sportfit.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {

    private Integer idNotificacion;
    private String tipo;
    private String titulo;
    private String mensaje;
    private Instant fNotificacion;
    private Boolean leido;
    private Integer idUsuario;
}