package com.upc.sportfit.repositorios;

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

}