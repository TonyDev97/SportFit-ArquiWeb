package com.upc.sportfit.repositorios;

import com.upc.sportfit.dtos.reportes.ReservaDeporteSede;
import com.upc.sportfit.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("select r from Reserva r " +
            "where r.sedeCancha.idSedeCancha = :idCancha " +
            "order by r.fReserva desc, r.hInicio asc")
    List<Reserva> listarPorCancha(
            @Param("idCancha") Integer idCancha
    );
}