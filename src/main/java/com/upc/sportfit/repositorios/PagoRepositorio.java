package com.upc.sportfit.repositorios;

import com.upc.sportfit.dtos.reportes.DetalleSedeDTO;
import com.upc.sportfit.dtos.reportes.DistribucionPagoDTO;
import com.upc.sportfit.dtos.reportes.IngresoDiarioDTO;
import com.upc.sportfit.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import com.upc.sportfit.entidades.Pago;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    @Query("SELECT new com.upc.sportfit.dtos.reportes.IngresoDiarioDTO(r.fReserva, SUM(p.montoTotal), COUNT(p.idPago)) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY r.fReserva " +
            "ORDER BY r.fReserva")
    List<IngresoDiarioDTO> obtenerIngresosPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT new com.upc.sportfit.dtos.reportes.DistribucionPagoDTO(p.metodo, SUM(p.montoTotal), COUNT(p.idPago)) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY p.metodo")
    List<DistribucionPagoDTO> obtenerDistribucionPorMetodoPago(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin")LocalDate fechaFin);

    // HU08 - ED22: Monto perdido por cancelaciones en un mes/año (desde Pago, sin tocar Reserva)
    @Query("SELECT COALESCE(SUM(p.montoTotal), 0) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND r.fReserva BETWEEN :fechaInicio AND :fechaFin")
    Number obtenerMontoPerdidoPorCancelaciones(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    List<Pago> findByReserva_IdReserva(Integer idReserva);

    // ED86: Detalle de reservas e ingresos por sede y deporte (sedeId null = todas las sedes)
    @Query("SELECT new com.upc.sportfit.dtos.reportes.DetalleSedeDTO(r.sedeCancha.sede.nombre, " +
            "r.sedeCancha.cancha.deporte, COUNT(DISTINCT r), SUM(p.montoTotal)) " +
            "FROM Pago p JOIN p.reserva r " +
            "WHERE r.estado = 'completada' " +
            "AND EXTRACT(MONTH FROM r.fReserva) = :mes " +
            "AND EXTRACT(YEAR FROM r.fReserva) = :anio " +
            "AND (:sedeId IS NULL OR r.sedeCancha.sede.idSede = :sedeId) " +
            "GROUP BY r.sedeCancha.sede.nombre, r.sedeCancha.cancha.deporte " +
            "ORDER BY SUM(p.montoTotal) DESC")
    List<DetalleSedeDTO> listarDetalleSede(@Param("mes") Integer mes,
                                           @Param("anio") Integer anio,
                                           @Param("sedeId") Integer sedeId);

}
