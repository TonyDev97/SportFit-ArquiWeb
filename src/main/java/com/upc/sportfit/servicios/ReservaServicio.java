package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    //CRUD
    public Reserva registrarReserva(Reserva reserva){
        return reservaRepositorio.save(reserva);
    }

    // Registrar reserva
    public List<Reserva> listarReservas(){
        return reservaRepositorio.findAll();
    }

    // Actualizar reserva
    public Reserva editarReserva(Reserva reserva){
        if (reservaRepositorio.existsById(reserva.getId())){
            return reservaRepositorio.save(reserva);
        }
        return null;
    }

    public void eliminarReserva(Integer id){
        reservaRepositorio.deleteById(id);
    }

    // Consultar Reserva por id
    public Reserva buscarReserva(Integer id){
        return reservaRepositorio.findById(id).orElse(null);
    }

    // Consultar Reserva de una cancha para una fecha
    public List<Reserva> listarReservaCancha(LocalDate fecha, Integer id){
        return reservaRepositorio.listarReservaCancha(fecha, id);
    }

    public List<Reserva> listarReservasCliente(Integer id){
        return reservaRepositorio.listarReservaCliente(id);
    }

    public List<Reserva> listarReservaConfirmada(){
        String confirmada = "Confirmada";
        return reservaRepositorio.findByEstado(confirmada);
    }

    public Reserva eliminarLogicoReserva(Integer id){
        Reserva reserva = reservaRepositorio.findById(id).orElse(null);
        if (reserva != null){
            reserva.setEstado("Eliminada");
            reserva.setFModificacion(Instant.now());
            return reservaRepositorio.save(reserva);
        }
        return null;
    }

}