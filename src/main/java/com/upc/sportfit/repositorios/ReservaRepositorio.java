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

    // Busca todas las reservas realizadas por un usuario.
    List<Reserva> findByIdUsuario(Integer idUsuario);


    // Busca las reservas de una cancha en una fecha determinada.
    List<Reserva> findByIdSedeCanchaAndFReserva(
            Integer idSedeCancha,
            LocalDate fecha
    );


    // Busca las reservas que ocupan un horario determinado.
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.idSedeCancha = :idSedeCancha
        AND r.fReserva = :fecha
        AND r.estado IN ('Solicitada', 'Confirmada')
        AND r.hInicio < :horaFin
        AND r.hFin > :horaInicio
    """)
    List<Reserva> encontrarConflictosDeHorario(
            @Param("idSedeCancha") Integer idSedeCancha,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );


    // Busca conflictos de horario excluyendo una reserva específica.
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.idSedeCancha = :idSedeCancha
        AND r.fReserva = :fecha
        AND r.estado IN ('Pendiente', 'Solicitada', 'Confirmada')
        AND r.id <> :idReserva
        AND r.hInicio < :horaFin
        AND r.hFin > :horaInicio
    """)
    List<Reserva> encontrarConflictosExcluyendoReserva(
            @Param("idSedeCancha") Integer idSedeCancha,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("idReserva") Integer idReserva
    );


    // Busca una reserva confirmada perteneciente a un usuario.
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.id = :idReserva
        AND r.idUsuario = :idUsuario
        AND r.estado = 'Confirmada'
    """)
    Reserva encontrarReservaConfirmadaPorUsuario(
            @Param("idReserva") Integer idReserva,
            @Param("idUsuario") Integer idUsuario
    );


    // Busca las reservas que se encuentran en un estado determinado.
    List<Reserva> findByEstado(String estado);


    // Busca las reservas de una cancha y fecha para consultar su disponibilidad.
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.idSedeCancha = :idSedeCancha
        AND r.fReserva = :fecha
    """)
    List<Reserva> encontrarDisponibilidadPorCanchaYFecha(
            @Param("idSedeCancha") Integer idSedeCancha,
            @Param("fecha") LocalDate fecha
    );
}