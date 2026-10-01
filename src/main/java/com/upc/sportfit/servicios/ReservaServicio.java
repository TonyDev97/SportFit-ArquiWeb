package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.reportes.*;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.repositorios.PagoRepositorio;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import com.upc.sportfit.repositorios.SedeCanchaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.upc.sportfit.dtos.EstadoReservaDTO;
import com.upc.sportfit.dtos.PagoDTO;
import com.upc.sportfit.dtos.SolicitudReservaDTO;
import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.PagoRepositorio;
import com.upc.sportfit.repositorios.SedeCanchaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class ReservaServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private SedeCanchaRepositorio sedeCanchaRepositorio;

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

    // HU10 ED16
    public List<ReservaDTO> listarReservasCliente(Integer id) {
        List<Reserva> reservas = reservaRepositorio.listarReservaCliente(id);
        List<ReservaDTO> reservaDTOs = new ArrayList<>();
        for (Reserva reserva : reservas) {
            reservaDTOs.add(
                    modelMapper.map(reserva, ReservaDTO.class)
            );
        }
        return reservaDTOs;
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

    // HU12 - Listar Solicitudes de Reserva por estado
    public List<ReservaDTO> listarReservasDTO(String estado) {
        List<Reserva> reservas;
        if (estado == null) {
            reservas = reservaRepositorio.findAll();
        } else {
            reservas = reservaRepositorio.findByEstado(estado);
        }
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    // HU13 - Consultar Detalle de Solicitud de Reserva y Pagos
    public SolicitudReservaDTO consultarSolicitud(Integer idReserva) {
        Reserva reserva = reservaRepositorio.findById(idReserva)
                .orElseThrow(() -> new NoSuchElementException("No existe la reserva con ID " + idReserva));

        List<PagoDTO> pagos = pagoRepositorio.findByReserva_IdReserva(idReserva)
                .stream()
                .map(pago -> modelMapper.map(pago, PagoDTO.class))
                .toList();

        SolicitudReservaDTO detalle = new SolicitudReservaDTO();
        detalle.setReserva(modelMapper.map(reserva, ReservaDTO.class));
        detalle.setPagos(pagos);

        return detalle;
    }

    // HU13 - Cambiar Estado de la Solicitud (Aceptar / Rechazar con validaciones)
    @Transactional
    public ReservaDTO cambiarEstadoSolicitud(Integer idReserva, EstadoReservaDTO dto) {
        if (!"Confirmada".equals(dto.getEstado()) && !"Rechazada".equals(dto.getEstado())) {
            throw new IllegalArgumentException("El estado debe ser Confirmada o Rechazada");
        }

        if (dto.getIdAdministrador() == null) {
            throw new IllegalArgumentException("Debe indicar el administrador");
        }

        Usuario administrador = usuarioRepositorio.findById(dto.getIdAdministrador())
                .orElseThrow(() -> new NoSuchElementException("El administrador no existe"));

        if (!Boolean.TRUE.equals(administrador.getActivo())
                || administrador.getRol() == null
                || !"ADMINISTRADOR".equalsIgnoreCase(administrador.getRol().getNombre())) {
            throw new IllegalArgumentException("Debe indicar un administrador activo");
        }

        Reserva reserva = reservaRepositorio.findById(idReserva)
                .orElseThrow(() -> new NoSuchElementException("No existe la reserva con ID " + idReserva));

        if (!"Solicitada".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden atender reservas en estado Solicitada");
        }

        List<Pago> pagos = pagoRepositorio.findByReserva_IdReserva(idReserva);
        boolean tieneComprobante = false;

        for (Pago pago : pagos) {
            if (pago.getUrlComprobante() != null && !pago.getUrlComprobante().trim().isEmpty()) {
                tieneComprobante = true;
                break;
            }
        }

        if (!tieneComprobante) {
            throw new IllegalArgumentException("La solicitud debe tener un pago con comprobante");
        }

        if ("Confirmada".equals(dto.getEstado())) {
            if (!Boolean.TRUE.equals(reserva.getSedeCancha().getEstado())) {
                throw new IllegalArgumentException("La cancha debe estar activa");
            }

            List<Reserva> reservasDelDia = reservaRepositorio.listarReservaCancha(
                    reserva.getFReserva(),
                    reserva.getSedeCancha().getIdSedeCancha()
            );

            for (Reserva otra : reservasDelDia) {
                if (!otra.getIdReserva().equals(reserva.getIdReserva())
                        && "Confirmada".equals(otra.getEstado())
                        && otra.getHInicio().isBefore(reserva.getHFin())
                        && otra.getHFin().isAfter(reserva.getHInicio())) {
                    throw new IllegalArgumentException("Ya existe una reserva confirmada en ese horario");
                }
            }
        }

        reserva.setEstado(dto.getEstado());
        reserva.setModificadoPor(administrador.getCorreo());
        reserva.setFModificacion(Instant.now());

        reserva = reservaRepositorio.save(reserva);

        return modelMapper.map(reserva, ReservaDTO.class);
    }

    // HU14 - Listar Reservas por Cancha
    public List<ReservaDTO> listarReservasPorCancha(Integer idCancha) {
        if (!sedeCanchaRepositorio.existsById(idCancha)) {
            throw new NoSuchElementException("No existe la cancha con ID " + idCancha);
        }

        return reservaRepositorio.listarPorCancha(idCancha)
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }


}