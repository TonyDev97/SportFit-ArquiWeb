package com.upc.sportfit.dtos;

import com.upc.sportfit.entidades.Sede;
import com.upc.sportfit.entidades.TipoCancha;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SedeCanchaDTO {

    private Integer idSedeCancha;
    private String nombre;
    private Boolean estado;
    private BigDecimal precio;
    private String imgCancha;
    private SedeDTO sede;
    private TipoCanchaDTO cancha;

}
