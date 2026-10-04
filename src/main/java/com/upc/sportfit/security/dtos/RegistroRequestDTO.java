package com.upc.sportfit.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequestDTO {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    private String contrasenaHash;
    private Boolean activo;
    private String imgUsuario;
    private Instant fCreacion;
    private Instant fModificacion;
}
/*
*/