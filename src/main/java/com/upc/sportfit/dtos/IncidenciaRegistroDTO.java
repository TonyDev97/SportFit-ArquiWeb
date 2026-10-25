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
public class IncidenciaRegistroDTO {
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El asunto no puede estar vacío")
    @Size(max = 100, message = "El asunto no puede exceder los 100 caracteres")
    private String asunto;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 300, message = "La descripción no puede exceder los 300 caracteres")
    private String descripcion;

    private UsuarioDTO usuario;
}
