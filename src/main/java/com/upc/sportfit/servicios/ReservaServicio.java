package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.reportes.*;
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

    //revisar esto si es valido porque asi lo estaba revisando
    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private ModelMapper modelMapper;

    //CRUD
    public Reserva registrarReserva(Reserva reserva){
        return reservaRepositorio.save(reserva);
    }

    // Registrar reserva
    public List<Reserva> listarReservas(){
        return reservaRepositorio.findAll();
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

    public List<Reserva> listarReservasSede(Integer id_sede) {
        return reservaRepositorio.listarReservasSede(id_sede);
    }

    public List<Reserva> listarReservasDeporte(String deporte){
        return reservaRepositorio.listarReservasDeporte(deporte);
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

    // HU08 - ED21: Cancelaciones por día
    public List<CancelacionDiaDTO> obtenerCancelacionesPorDia(Integer mes, Integer anio) {
        List<CancelacionDiaDTO> cancelaciones = reservaRepositorio.obtenerCancelacionesPorDia(mes, anio);
        if (cancelaciones.isEmpty()) {
            throw new RuntimeException("No se encontraron cancelaciones para el período seleccionado");
        }
        return cancelaciones;
    }

    // HU08 - ED22: Monto perdido por cancelaciones (delegado a PagoServicio)
    public MontoPerdidoDTO obtenerMontoPerdidoPorCancelaciones(Integer mes, Integer anio) {
        return pagoServicio.obtenerMontoPerdidoPorCancelaciones(mes, anio);
    }

    // HU08 - ED23: Total_cancelaciones
    public TotalCancelacionesDTO obtenerTotalCancelaciones(Integer mes, Integer anio) {
        return reservaRepositorio.obtenerTotalCancelaciones(mes, anio);
    }

    // HU09 - ED24: Reservas por cliente
    public List<ReservasPorClienteDTO> obtenerReservasPorCliente(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReservasPorClienteDTO> reservas = reservaRepositorio.obtenerReservasPorCliente(fechaInicio, fechaFin);
        if (reservas.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas para los criterios seleccionados");
        }
        return reservas;
    }

    // HU09 - ED25: Reservas por cliente con filtro mínimo
    public List<ReservasPorClienteDTO> obtenerReservasPorClienteMinimo(LocalDate fechaInicio, LocalDate fechaFin, Long minReservas) {
        List<ReservasPorClienteDTO> reservas = reservaRepositorio.obtenerReservasPorClienteMinimo(fechaInicio, fechaFin, minReservas);
        if (reservas.isEmpty()) {
            throw new RuntimeException("No se encontraron clientes que cumplan con el mínimo de reservas");
        }
        return reservas;
    }

    // HU09 - ED26: Top clientes con más reservas
    public List<ReservasPorClienteDTO> obtenerTopClientesReservas(LocalDate fechaInicio, LocalDate fechaFin, Integer top) {
        List<ReservasPorClienteDTO> reservas = reservaRepositorio.obtenerTopClientesReservas(fechaInicio, fechaFin, top);
        if (reservas.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas para los criterios seleccionados");
        }
        return reservas.subList(0, Math.min(top, reservas.size()));
    }


}