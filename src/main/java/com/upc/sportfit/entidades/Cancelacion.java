package com.upc.sportfit.entidades;

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
@Table(name = "cancelacion")
public class Cancelacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancelacion", nullable = false)
    private Integer idCancelacion;

    @Size(max = 25)
    @NotNull
    @Column(name = "tipo_cancelacion", nullable = false, length = 25)
    private String tipoCancelacion;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "f_cancelacion", nullable = false)
    private Instant fCancelacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;


}