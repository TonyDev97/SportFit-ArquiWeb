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
@Table(name = "incidencia")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Size(max = 100)
    @NotNull
    @Column(name = "asunto", nullable = false, length = 100)
    private String asunto;

    @Size(max = 300)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    @Size(max = 20)
    @NotNull
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Size(max = 300)
    @Column(name = "respuesta_admin", length = 300)
    private String respuestaAdmin;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "f_creacion", nullable = false)
    private Instant fCreacion;

    @Column(name = "f_respuesta")
    private Instant fRespuesta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;


}