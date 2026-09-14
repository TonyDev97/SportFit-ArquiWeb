package com.upc.sporfit.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 100)
    @NotNull
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Size(max = 8)
    @NotNull
    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Size(max = 9)
    @NotNull
    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Size(max = 100)
    @NotNull
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Size(max = 255)
    @NotNull
    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Size(max = 300)
    @Column(name = "img_usuario", length = 300)
    private String imgUsuario;

    @Size(max = 100)
    @NotNull
    @Column(name = "creado_por", nullable = false, length = 100)
    private String creadoPor;

    @Size(max = 100)
    @Column(name = "modificado_por", length = 100)
    private String modificadoPor;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "f_creacion", nullable = false)
    private Instant fCreacion;

    @Column(name = "f_modificacion")
    private Instant fModificacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;


}