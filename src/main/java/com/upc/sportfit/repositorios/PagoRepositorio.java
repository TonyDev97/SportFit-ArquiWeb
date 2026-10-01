package com.upc.sportfit.repositorios;

import com.upc.sportfit.dtos.reportes.IngresoDiarioDTO;
import com.upc.sportfit.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    @Query("SELECT new com.upc.sportfit.dtos.reportes.IngresoDiarioDTO(r.fReserva, SUM(p.montoTotal)) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY r.fReserva " +
            "ORDER BY r.fReserva")
    List<IngresoDiarioDTO> obtenerIngresosPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin")LocalDate fechaFin);

    // HU08 - ED22: Monto perdido por cancelaciones en un mes/año (desde Pago, sin tocar Reserva)
    @Query("SELECT COALESCE(SUM(p.montoTotal), 0) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND EXTRACT(MONTH FROM r.fReserva) = :mes " +
            "AND EXTRACT(YEAR FROM r.fReserva) = :anio")
    Number obtenerMontoPerdidoPorCancelaciones(@Param("mes") Integer mes, @Param("anio") Integer anio);
}
