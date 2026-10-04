package com.upc.sportfit.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequestDTO {
    @NotBlank
    @Email
    private String correo;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    @Size(min = 9, max = 9)
    private String telefono;
}