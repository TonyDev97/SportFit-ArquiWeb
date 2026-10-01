package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    @GetMapping("/api")
    public List<Reserva> listarReservas(){
        return reservaServicio.listarReservas();
    }

    // GET /api/reserva/disponibilidad/{idCancha}?fecha=2026-09-15
    @GetMapping("/reserva/disponibilidad/{idCancha}")
    public List<Reserva> listarReservaCancha(@PathVariable Integer idCancha,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return reservaServicio.listarReservaCancha(fecha, idCancha);
    }

    @GetMapping("/reserva/usuario/{idUsuario}")
    public List<Reserva> listarReservasCliente(@PathVariable Integer idUsuario){
        return reservaServicio.listarReservasCliente(idUsuario);
    }

    @GetMapping("/reserva-validar")
    public List<Reserva> listarReservaConfirmada(){
        return reservaServicio.listarReservaConfirmada();
    }

    @PostMapping("/reserva")
    public Reserva registrarReserva(@RequestBody Reserva reserva){
        return reservaServicio.registrarReserva(reserva);
    }

    @PutMapping("/reserva-actualizar")
    public Reserva editarReserva(@RequestBody Reserva reserva){
        return reservaServicio.editarReserva(reserva);
    }

    @PutMapping("/reserva-cancelar/{idReserva}")
    public Reserva eliminarLogicoReserva(@PathVariable Integer idReserva){
        return reservaServicio.eliminarLogicoReserva(idReserva);
    }

    @DeleteMapping("/reserva-eliminar/{id}")
    public void eliminarReserva(@RequestBody Integer id){
        reservaServicio.eliminarReserva(id);
    }


}