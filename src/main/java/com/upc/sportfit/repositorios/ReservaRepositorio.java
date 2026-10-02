package com.upc.sportfit.repositorios;

import com.upc.sportfit.dtos.reportes.CancelacionDiaDTO;
import com.upc.sportfit.dtos.reportes.ReservaDeporteSede;
import com.upc.sportfit.dtos.reportes.ReservasPorClienteDTO;
import com.upc.sportfit.dtos.reportes.TotalCancelacionesDTO;
import com.upc.sportfit.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Integer> {

    // Consultar Reserva de una cancha para una fecha
    // Verificar funcionalidad
    @Query("select r from Reserva r join r.sedeCancha sc where r.fReserva =:fecha_reserva and sc.idSedeCancha =:id")
    List<Reserva> listarReservaCancha(@Param("fecha_reserva") LocalDate fechaReserva, @Param("id") Integer id);

    @Query("select r from Reserva r join r.usuario cl where cl.idUsuario =:id")
    List<Reserva> listarReservaCliente(@Param("id") Integer id);

    List<Reserva> findByEstado(String estado);

    // Listar reservas según fecha, deporte y sede
    @Query("select r from Reserva r where r.fReserva = :fecha and r.sedeCancha.cancha.idTipoCancha = :idDeporte and r.sedeCancha.sede.idSede = :idSede")
    List<Reserva> ListarReservasPorFechaDeporteSede(@Param("fecha") LocalDate fecha,
                                          @Param("idDeporte") Integer idDeporte,
                                          @Param("idSede") Integer idSede);

    @Query("select r from Reserva r where r.sedeCancha.sede.idSede = :id_sede")
    List<Reserva> listarReservasSede(@Param("id_sede") Integer id_sede);

    @Query("select r from Reserva r where r.sedeCancha.cancha.deporte = :deporte")
    List<Reserva> listarReservasDeporte(@Param("deporte") String deporte);

    // OBTENER LA FRECUENCIA DE RESERVAS POR TIPO DE DEPORTE PARA TODAS LAS SEDES (ME FALTA PROBAR)
    @Query("select new com.upc.sportfit.dtos.reportes.ReservaDeporteSede(r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre, " +
            "cast(count(r) as Integer)) from Reserva r group by r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre")
    List<ReservaDeporteSede> frecuenciaReservasPorDeporteSede();

    // OBTENER LA FRECUENCIA DE RESERVAS POR TIPO DE DEPORTE PARA TODAS LAS SEDES FILTRADO ENTRE FECHAS (FALTA PROBAR)
    @Query("select new com.upc.sportfit.dtos.reportes.ReservaDeporteSede(r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre, " +
            "cast(count(r) as Integer)) from Reserva r where r.fReserva between :fecha_inicio and :fecha_fin " +
            "group by r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre")
    List<ReservaDeporteSede> frecuenciaReservasPorDeporteSedeEntreFechas(@Param("fecha_inicio") LocalDate fechaInicio, @Param("fecha_fin") LocalDate fechaFin);

    // HU08 - ED21: Cancelaciones por día en un mes/año
    @Query("SELECT new com.upc.sportfit.dtos.reportes.CancelacionDiaDTO(r.fReserva, COUNT(r)) " +
            "FROM Reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND EXTRACT(MONTH FROM r.fReserva) = :mes " +
            "AND EXTRACT(YEAR FROM r.fReserva) = :anio " +
            "GROUP BY r.fReserva " +
            "ORDER BY r.fReserva")
    List<CancelacionDiaDTO> obtenerCancelacionesPorDia(@Param("mes") Integer mes, @Param("anio") Integer anio);

    // HU08 - ED23: Total de reservas canceladas en un mes/año
    @Query("SELECT new com.upc.sportfit.dtos.reportes.TotalCancelacionesDTO(COUNT(r)) " +
            "FROM Reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND EXTRACT(MONTH FROM r.fReserva) = :mes " +
            "AND EXTRACT(YEAR FROM r.fReserva) = :anio")
    TotalCancelacionesDTO obtenerTotalCancelaciones(@Param("mes") Integer mes, @Param("anio") Integer anio);

    // HU09 - ED24: Reservas por cliente en un período
    @Query("SELECT new com.upc.sportfit.dtos.reportes.ReservasPorClienteDTO(u.idUsuario, CONCAT(u.nombre, ' ', u.apellido), COUNT(r)) " +
            "FROM Reserva r JOIN r.usuario u " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY u.idUsuario, u.nombre, u.apellido " +
            "ORDER BY COUNT(r) DESC")
    List<ReservasPorClienteDTO> obtenerReservasPorCliente(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // HU09 - ED25: Reservas por cliente con filtro mínimo
    @Query("SELECT new com.upc.sportfit.dtos.reportes.ReservasPorClienteDTO(u.idUsuario, CONCAT(u.nombre, ' ', u.apellido), COUNT(r)) " +
            "FROM Reserva r JOIN r.usuario u " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY u.idUsuario, u.nombre, u.apellido " +
            "HAVING COUNT(r) >= :minReservas " +
            "ORDER BY COUNT(r) DESC")
    List<ReservasPorClienteDTO> obtenerReservasPorClienteMinimo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("minReservas") Long minReservas);

    // HU09 - ED26: Top de clientes con más reservas
    @Query("SELECT new com.upc.sportfit.dtos.reportes.ReservasPorClienteDTO(u.idUsuario, CONCAT(u.nombre, ' ', u.apellido), COUNT(r)) " +
            "FROM Reserva r JOIN r.usuario u " +
            "WHERE r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY u.idUsuario, u.nombre, u.apellido " +
            "ORDER BY COUNT(r) DESC")
    List<ReservasPorClienteDTO> obtenerTopClientesReservas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("top") Integer top);

    @Query("select r from Reserva r " +
            "where r.sedeCancha.idSedeCancha = :idCancha " +
            "order by r.fReserva desc, r.hInicio asc")
    List<Reserva> listarPorCancha(
            @Param("idCancha") Integer idCancha
    );
}