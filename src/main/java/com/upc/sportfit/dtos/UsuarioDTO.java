package com.upc.sportfit.dtos;

import com.upc.sportfit.entidades.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private String contrasenaHash;
    private Boolean activo;
    private String imgUsuario;
    private Instant fCreacion;
    private Instant fModificacion;
    private RolDTO rol;
}
