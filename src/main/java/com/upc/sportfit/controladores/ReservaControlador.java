package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;


    // Registra una nueva reserva.
    @PostMapping("/reserva")
    public Reserva registrar(@RequestBody Reserva reserva) {
        return reservaServicio.registrar(reserva);
    }


    // Lista todas las reservas registradas.
    @GetMapping("/reservas")
    public List<Reserva> listar() {
        return reservaServicio.listar();
    }


    // Busca una reserva por su ID.
    @GetMapping("/reserva/{id}")
    public Reserva listarPorId(@PathVariable Integer id) {
        return reservaServicio.listarPorId(id);
    }


    // Busca las reservas realizadas por un usuario.
    @GetMapping("/reserva/usuario/{idUsuario}")
    public List<Reserva> encontrarPorUsuario(
            @PathVariable Integer idUsuario) {

        return reservaServicio.encontrarPorUsuario(idUsuario);
    }


    // Consulta las reservas de una cancha para una fecha.
    @GetMapping("/reserva/disponibilidad/{idSedeCancha}")
    public List<Reserva> consultarDisponibilidad(
            @PathVariable Integer idSedeCancha,
            @RequestParam LocalDate fecha) {

        return reservaServicio.consultarDisponibilidad(
                idSedeCancha,
                fecha
        );
    }


    // Verifica si un horario se encuentra disponible.
    @GetMapping("/reserva/disponibilidad/validar/{idSedeCancha}")
    public boolean validarHorarioDisponible(
            @PathVariable Integer idSedeCancha,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime horaInicio,
            @RequestParam LocalTime horaFin) {

        return reservaServicio.validarHorarioDisponible(
                idSedeCancha,
                fecha,
                horaInicio,
                horaFin
        );
    }


    // Modifica la fecha, horario o cancha de una reserva.
    @PutMapping("/reserva/{id}")
    public Reserva actualizar(
            @PathVariable Integer id,
            @RequestBody Reserva reserva) {

        return reservaServicio.actualizar(id, reserva);
    }


    // Cancela una reserva.
    @PutMapping("/reserva/{id}/estado")
    public Reserva cancelar(@PathVariable Integer id) {
        return reservaServicio.cancelar(id);
    }
}