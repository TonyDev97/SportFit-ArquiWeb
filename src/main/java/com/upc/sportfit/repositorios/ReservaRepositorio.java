package com.upc.sportfit.repositorios;

import com.upc.sportfit.dtos.reportes.*;
import com.upc.sportfit.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    // ED18 OBTENER LA FRECUENCIA DE RESERVAS POR TIPO DE DEPORTE PARA TODAS LAS SEDES POR MES (DEFECTO ULTIMO MES)
    @Query("select new com.upc.sportfit.dtos.reportes.ReservaDeporteSedeDTO(r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre, " +
            "cast(count(r) as Integer)) from Reserva r where r.estado = 'completada' " +
            "and extract(month from r.fReserva) = :mes group by r.sedeCancha.cancha.deporte, r.sedeCancha.sede.nombre")
    List<ReservaDeporteSedeDTO> frecuenciaReservasPorDeporteSede(@Param("mes") Integer mes);


    // HU08 - ED21: Cancelaciones por día en un mes/año
    @Query("SELECT new com.upc.sportfit.dtos.reportes.CancelacionDiaDTO(r.fReserva, COUNT(r)) " +
            "FROM Reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY r.fReserva " +
            "ORDER BY r.fReserva")
    List<CancelacionDiaDTO> obtenerCancelacionesPorPeriodo(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // HU08 - ED23: Total de reservas canceladas en un mes/año
    @Query("SELECT new com.upc.sportfit.dtos.reportes.TotalCancelacionesDTO(COUNT(r)) " +
            "FROM Reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND r.fReserva BETWEEN :fechaInicio AND :fechaFin")
    TotalCancelacionesDTO obtenerTotalCancelaciones(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    //HU08 -ED84 Obenetmos la sitribucion por motivos de cancelacion
    @Query("SELECT new com.upc.sportfit.dtos.reportes.MotivoCancelacionDTO(c.tipoCancelacion, COUNT(c)) " +
            "FROM Cancelacion c JOIN c.reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND r.fReserva BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY c.tipoCancelacion " +
            "ORDER BY COUNT(c) DESC")
    List<MotivoCancelacionDTO> obtenerDistribucionPorMotivoCancelacion(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    //HU08 - ED85 Obtenemos tendencias anuales de cancelaciones
    @Query("SELECT new com.upc.sportfit.dtos.reportes.TendenciaCancelacionDTO(EXTRACT(MONTH FROM r.fReserva), c.tipoCancelacion, COUNT(c)) " +
            "FROM Cancelacion c JOIN c.reserva r " +
            "WHERE r.estado = 'Cancelada' " +
            "AND EXTRACT(YEAR FROM r.fReserva) = :anio " +
            "GROUP BY EXTRACT(MONTH FROM r.fReserva), c.tipoCancelacion " +
            "ORDER BY EXTRACT(MONTH FROM r.fReserva)")
    List<TendenciaCancelacionDTO> obtenerTendenciaAnualCancelaciones(@Param("anio") Integer anio);

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

    // HU09 - 87: Reporte consolidado de reservas por cliente con filtros dinámicos
    @Query("SELECT new com.upc.sportfit.dtos.reportes.ReservaClienteDTO(" +
            "CAST(u.idUsuario AS string), " +
            "CONCAT(CONCAT(u.nombre, ' '), u.apellido), " +
            "MAX(r.fReserva), " +
            "COUNT(r.idReserva)) " +
            "FROM Reserva r JOIN r.usuario u " +
            "WHERE r.estado = 'Confirmada' " +
            "AND (:fechaDesde IS NULL OR r.fReserva >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR r.fReserva <= :fechaHasta) " +
            "GROUP BY u.idUsuario, u.nombre, u.apellido " +
            "HAVING (:minReservas IS NULL OR COUNT(r.idReserva) >= :minReservas) " +
            "AND (:maxReservas IS NULL OR COUNT(r.idReserva) <= :maxReservas) " +
            "ORDER BY COUNT(r.idReserva) DESC")
    List<ReservaClienteDTO> obtenerReservasPorClienteConsolidado(
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("minReservas") Integer minReservas,
            @Param("maxReservas") Integer maxReservas,
            @Param("top") Integer top
    );

    //HU10 - ED24 Obtener el número de reservas de un cliente
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario.idUsuario = :idUsuario")
    Long contarReservasPorCliente(Integer idUsuario);

    // HU10 - ED25 - Obtener cantidad de próximas reservas de un cliente
    @Query("SELECT COUNT(r) FROM Reserva r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.estado = 'confirmada'")
    Long contarReservasProximas(@Param("idUsuario") Integer idUsuario);

    // HU10 - ED26 - Obtener cantidad de reservas canceladas de un cliente
    @Query("SELECT COUNT(r) FROM Reserva r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.estado = 'cancelada'")
    Long contarReservasCanceladas(@Param("idUsuario") Integer idUsuario);

    // HU10 - ED27 ED28 - Obtener próximas reservas de un cliente
    List<Reserva> findByUsuario_IdUsuarioAndEstado(Integer idUsuario, String estado);

    // HU02 - ED31 - Filtros de reservas
    @Query("select r from Reserva r where (:estado IS NULL OR r.estado = :estado) AND (:idSede IS NULL OR r.sedeCancha.sede.idSede = :idSede) AND (:fecha IS NULL OR r.fReserva = :fecha) AND (:idTipoDeporte IS NULL OR r.sedeCancha.cancha.idTipoCancha = :idTipoDeporte)")
    List<Reserva> filtrarReservas(@Param("estado") Boolean estado,@Param("idSede") Integer idSede,@Param("fecha") LocalDate fecha,@Param("idTipoDeporte") Integer idTipoDeporte);



}