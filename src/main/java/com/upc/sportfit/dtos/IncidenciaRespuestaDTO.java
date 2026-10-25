package com.upc.sportfit.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaRespuestaDTO {
    @NotNull(message = "El ID de la incidencia es obligatorio")
    private Integer idIncidencia;

    @NotBlank(message = "La respuesta del administrador no puede estar vacía")
    @Size(max = 300, message = "La respuesta no puede exceder los 300 caracteres")
    private String respuestaAdmin;
}
