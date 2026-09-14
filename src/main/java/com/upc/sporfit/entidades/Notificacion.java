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
@Table(name = "notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Size(max = 100)
    @NotNull
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Size(max = 300)
    @NotNull
    @Column(name = "mensaje", nullable = false, length = 300)
    private String mensaje;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "f_notificacion", nullable = false)
    private Instant fNotificacion;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "leido", nullable = false)
    private Boolean leido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;


}