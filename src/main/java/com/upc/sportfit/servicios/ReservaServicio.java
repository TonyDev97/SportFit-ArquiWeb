package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.reportes.ReservaDeporteSede;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    // Registrar reserva
    public ReservaDTO registrarReserva(ReservaDTO reservaDTO){

        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        reservaRepositorio.save(reserva);
        return modelMapper.map(reserva, ReservaDTO.class);
    }

    // Listar
    public List<ReservaDTO> listarReservas(){

        return reservaRepositorio.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    // Actualizar reserva
    public ReservaDTO editarReserva(ReservaDTO reservaDTO){
        return reservaRepositorio.findById(reservaDTO.getIdReserva())
                .map(
                        reserva -> {
                            modelMapper.map(reserva, reservaDTO);
                            return modelMapper.map(reservaRepositorio.save(reserva), ReservaDTO.class);
                        }
                )
                .orElseThrow(() -> new RuntimeException("No existe la reserva con ese id:" + reservaDTO.getIdReserva()));
    }

    public void eliminarReserva(Integer id){

        reservaRepositorio.deleteById(id);
    }

    // Consultar Reserva por id
    public Reserva buscarReserva(Integer id){
        return reservaRepositorio.findById(id).orElse(null);
    }

    // Consultar Reserva de una cancha para una fecha
    public List<ReservaDTO> listarReservaCancha(LocalDate fecha, Integer id){
        return reservaRepositorio.listarReservaCancha(fecha, id).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    public List<Reserva> listarReservasCliente(Integer id){
        return reservaRepositorio.listarReservaCliente(id);
    }

    public List<Reserva> listarReservaConfirmada(){
        String confirmada = "Confirmada";
        return reservaRepositorio.findByEstado(confirmada);
    }

    // Eliminar logico de la reserva
    public ReservaDTO eliminarLogicoReserva(Integer id){
        return reservaRepositorio.findById(id)
                .map(
                        reserva -> {
                            reserva.setEstado("Eliminada");
                            reserva.setFModificacion(Instant.now());
                            return modelMapper.map(reservaRepositorio.save(reserva), ReservaDTO.class);
                        }
                )
                .orElseThrow(() -> new RuntimeException("No existe la reserva con ese id:" + id));
    }

    public List<ReservaDTO> listarReservasSede(Integer id_sede) {

        return reservaRepositorio.listarReservasSede(id_sede).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    public List<ReservaDTO> listarReservasDeporte(String deporte){
        return reservaRepositorio.listarReservasDeporte(deporte).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    public List<ReservaDeporteSede> frecuenciaReservasPorDeporteSede(){
        List<ReservaDeporteSede> reportes = reservaRepositorio.frecuenciaReservasPorDeporteSede();
        if (reportes.isEmpty()){
            throw new RuntimeException("No hay información suficiente");
        }
        return reportes;
    }

    public List<ReservaDeporteSede> frecuenciaReservasPorDeporteSedeEntreFechas(LocalDate fechaMin, LocalDate fechaMax){
        List<ReservaDeporteSede> reportes = reservaRepositorio.frecuenciaReservasPorDeporteSedeEntreFechas(fechaMin, fechaMax);
        if (reportes.isEmpty()){
            throw new RuntimeException("No hay información suficiente");
        }
        return reportes;
    }


}