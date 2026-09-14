package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;


    // Registra una nueva reserva.
    public Reserva registrar(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }


    // Lista todas las reservas registradas.
    public List<Reserva> listar() {
        return reservaRepositorio.findAll();
    }


    // Busca una reserva por su ID.
    public Reserva listarPorId(Integer id) {
        return reservaRepositorio.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Error: Reserva no encontrada con ID " + id));
    }


    // Busca todas las reservas realizadas por un usuario.
    public List<Reserva> encontrarPorUsuario(Integer idUsuario) {
        return reservaRepositorio.findByIdUsuario(idUsuario);
    }


    // Busca las reservas de una cancha en una fecha determinada.
    public List<Reserva> consultarDisponibilidad(
            Integer idSedeCancha,
            LocalDate fecha) {

        return reservaRepositorio.findByIdSedeCanchaAndFReserva(
                idSedeCancha,
                fecha
        );
    }


    // Verifica si existe un conflicto con el horario seleccionado.
    public boolean validarHorarioDisponible(
            Integer idSedeCancha,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        List<Reserva> conflictos =
                reservaRepositorio.encontrarConflictosDeHorario(
                        idSedeCancha,
                        fecha,
                        horaInicio,
                        horaFin
                );

        return conflictos.isEmpty();
    }


    // Actualiza los datos de una reserva.
    public Reserva actualizar(
            Integer id,
            Reserva datosActualizados) {

        Reserva reservaExistente = reservaRepositorio.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Error: Reserva no encontrada con ID " + id));

        reservaExistente.setFReserva(
                datosActualizados.getFReserva());

        reservaExistente.setHInicio(
                datosActualizados.getHInicio());

        reservaExistente.setHFin(
                datosActualizados.getHFin());

        reservaExistente.setIdSedeCancha(
                datosActualizados.getIdSedeCancha());

        return reservaRepositorio.save(reservaExistente);
    }


    // Cancela una reserva cambiando su estado a Cancelada.
    public Reserva cancelar(Integer id) {

        Reserva reserva = reservaRepositorio.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Error: Reserva no encontrada con ID " + id));

        reserva.setEstado("Cancelada");

        return reservaRepositorio.save(reserva);
    }
}