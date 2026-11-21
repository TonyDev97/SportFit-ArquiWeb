package com.upc.sportfit.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    //utilizamos esto para el cambio de contraseña y poder un cifrado BCrypt investigación propia no enseñado por el profesor
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenaHash;
    private Boolean activo;
    private String imgUsuario;
    private Instant fCreacion;
    private Instant fModificacion;
    private Integer idRol;
}
