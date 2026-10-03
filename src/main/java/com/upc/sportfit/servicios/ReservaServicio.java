package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.*;
import com.upc.sportfit.dtos.reportes.*;
import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.PagoRepositorio;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import com.upc.sportfit.repositorios.SedeCanchaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private ModelMapper modelMapper;

    // Registrar reserva
    public ReservaDTO registrarReserva(ReservaDTO reservaDTO){
        List<String> estadosValidos = List.of("cancelada", "solicitada", "rechazada", "confirmada", "completada");
        String estadoIngresado = reservaDTO.getEstado();

        if (estadoIngresado == null || !estadosValidos.contains(estadoIngresado.trim().toLowerCase())) {
            throw new RuntimeException("Estado inválido. Solo se permite: " + String.join(", ", estadosValidos));
        }

        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        reserva.setEstado(estadoIngresado.trim().toLowerCase());
        reserva = reservaRepositorio.save(reserva);
        return modelMapper.map(reserva, ReservaDTO.class);
    }

    // Listar
    public List<ReservaDTO> listarReservas(){
        List<ReservaDTO> lista = reservaRepositorio.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();

        if (lista.isEmpty()) {
            throw new RuntimeException("No existen reservas registradas");
        }
        return lista;
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

    @Transactional
    public ReservaDTO ActualizarEstado(Integer idReserva, String nuevoEstado) {
        List<String> estadosValidos = List.of("cancelada", "solicitada", "rechazada", "confirmada", "completada");

        if (nuevoEstado == null || !estadosValidos.contains(nuevoEstado.trim().toLowerCase())) {
            throw new RuntimeException("Estado inválido. Solo se permite: " + String.join(", ", estadosValidos));
        }

        return reservaRepositorio.findById(idReserva)
                .map(reserva -> {
                    reserva.setEstado(nuevoEstado.trim().toLowerCase());
                    return modelMapper.map(reservaRepositorio.save(reserva), ReservaDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("No existe la reserva con el id: " + idReserva));
    }

    public void eliminarReserva(Integer id){

        reservaRepositorio.deleteById(id);
    }

    // Consultar Reserva por id
    public Reserva buscarReserva(Integer id){
        return reservaRepositorio.findById(id).orElse(null);
    }

    // Consultar reservas por fecha, deporte y sede
    public List<ReservaDTO> listarReservaCancha(LocalDate fecha, Integer idDeporte){
        return reservaRepositorio.listarReservaCancha(fecha, idDeporte).stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .toList();
    }

    public List<ReservaHorarioDTO> ListarReservasPorFechaDeporteSede(LocalDate fecha, Integer idDeporte, Integer idSede) {
        List<Reserva> reservas = reservaRepositorio.ListarReservasPorFechaDeporteSede(fecha, idDeporte, idSede);

        return reservas.stream()
                .map(reserva -> {
                    ReservaHorarioDTO dto = new ReservaHorarioDTO();
                    dto.setIdReserva(reserva.getIdReserva());

                    if (reserva.getSedeCancha() != null) {
                        dto.setIdSedeCancha(reserva.getSedeCancha().getIdSedeCancha());
                        dto.setPrecio(reserva.getSedeCancha().getPrecio());

                        if (reserva.getSedeCancha().getCancha() != null) {
                            dto.setAforo(reserva.getSedeCancha().getCancha().getAforo());
                        }
                    }

                    dto.setHoraInicio(reserva.getHInicio());
                    dto.setHoraFin(reserva.getHFin());
                    return dto;
                })
                .toList();
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

    // Eliminar logico de la reserva, si tiene estado confirmada
    public ReservaDTO eliminarLogicoReserva(Integer id) {
        return reservaRepositorio.findById(id)
                .map(reserva -> {
                    if (!"confirmada".equalsIgnoreCase(reserva.getEstado())) {
                        throw new RuntimeException("Solo se pueden cancelar reservas en estado 'confirmada'. Estado actual: " + reserva.getEstado());
                    }

                    reserva.setEstado("cancelada");
                    reserva.setFModificacion(Instant.now());
                    return modelMapper.map(reservaRepositorio.save(reserva), ReservaDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("No existe la reserva con ese id: " + id));
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


    public List<ReservaDeporteSedeDTO> frecuenciaReservasPorDeporteSede(Integer mes){
        if (mes == null) {
            mes = LocalDate.now().getMonthValue();
        }
        List<ReservaDeporteSedeDTO> reportes = reservaRepositorio.frecuenciaReservasPorDeporteSede(mes);
        if (reportes.isEmpty()){
            throw new RuntimeException("No hay información suficiente");
        }
        return reportes;
    }

    // HU08 - Validación y cálculo de rango de fechas (mes opcional, año obligatorio)
    private LocalDate[] validarYCalcularRangoFechas(Integer mes, Integer anio) {
        int anioActual = Year.now().getValue();

        if (anio == null || anio < 2000 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe ser un número válido entre 2000 y " + anioActual);
        }

        if (mes != null && (mes < 1 || mes > 12)) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }

        LocalDate fechaInicio;
        LocalDate fechaFin;

        if (mes != null) {
            fechaInicio = LocalDate.of(anio, mes, 1);
            fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
        } else {
            fechaInicio = LocalDate.of(anio, 1, 1);
            fechaFin = LocalDate.of(anio, 12, 31);
        }

        return new LocalDate[]{fechaInicio, fechaFin};
    }

    // HU08 - ED21: Cancelaciones por día
    public List<CancelacionDiaDTO> obtenerCancelacionesPorPeriodo(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        List<CancelacionDiaDTO> cancelaciones = reservaRepositorio.obtenerCancelacionesPorPeriodo(rango[0], rango[1]);
        if (cancelaciones.isEmpty()) {
            throw new RuntimeException("No se encontraron cancelaciones para el período seleccionado");
        }
        return cancelaciones;
    }

    // HU08 - ED22: Monto perdido por cancelaciones (delegado a PagoServicio)
    public MontoPerdidoDTO obtenerMontoPerdidoPorCancelaciones(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        return pagoServicio.obtenerMontoPerdidoPorCancelaciones(rango[0], rango[1]);
    }

    // HU08 - ED23: Total cancelaciones
    public TotalCancelacionesDTO obtenerTotalCancelaciones(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        return reservaRepositorio.obtenerTotalCancelaciones(rango[0], rango[1]);
    }

    // HU08 - ED84: Distribución por motivo de cancelación
    public List<MotivoCancelacionDTO> obtenerDistribucionPorMotivoCancelacion(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        return reservaRepositorio.obtenerDistribucionPorMotivoCancelacion(rango[0], rango[1]);
    }

    // HU08 - ED85: Tendencia anual de cancelaciones
    public List<TendenciaCancelacionDTO> obtenerTendenciaAnualCancelaciones(Integer anio) {
        if (anio == null) {
            throw new IllegalArgumentException("El año es obligatorio");
        }
        int anioActual = Year.now().getValue();
        if (anio < 2000 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe ser un número válido entre 2000 y " + anioActual);
        }
        return reservaRepositorio.obtenerTendenciaAnualCancelaciones(anio);
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

    // HU09 - ED87: Reporte consolidado de reservas por cliente
    public List<ReservaClienteDTO> obtenerReservasPorClienteConsolidado(
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            Integer minReservas,
            Integer maxReservas,
            Integer top) {

        // Validaciones
        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("La fechaDesde no puede ser posterior a fechaHasta");
        }

        if (minReservas != null && minReservas < 0) {
            throw new IllegalArgumentException("minReservas no puede ser negativo");
        }

        if (maxReservas != null && maxReservas < 0) {
            throw new IllegalArgumentException("maxReservas no puede ser negativo");
        }

        if (minReservas != null && maxReservas != null && minReservas > maxReservas) {
            throw new IllegalArgumentException("minReservas no puede ser mayor que maxReservas");
        }

        if (top != null && top < 0) {
            throw new IllegalArgumentException("top no puede ser negativo");
        }

        List<ReservaClienteDTO> resultados = reservaRepositorio.obtenerReservasPorClienteConsolidado(
                fechaDesde, fechaHasta, minReservas, maxReservas, top);

        // Aplicar límite top en Service si no se hizo en la query
        if (top != null && top > 0 && resultados.size() > top) {
            return resultados.subList(0, top);
        }

        return resultados;
    }

    // HU13: Listar reservas con filtro opcional por estado.
    public List<ReservaDTO> listarReservasDTO(String estado) {

        List<Reserva> reservas = reservaRepositorio.findAll();
        List<ReservaDTO> resultado = new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (estado == null
                    || estado.equalsIgnoreCase(reserva.getEstado())) {

                resultado.add(
                        modelMapper.map(reserva, ReservaDTO.class)
                );
            }
        }

        return resultado;
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

        if (!"Solicitada".equals(reserva.getEstado())
                && !"solicitada".equals(reserva.getEstado())) {

            throw new IllegalArgumentException(
                    "Solo se pueden atender reservas en estado Solicitada");
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
                        && ("Confirmada".equals(otra.getEstado())
                        || "confirmada".equals(otra.getEstado()))
                        && otra.getHInicio().isBefore(reserva.getHFin())
                        && otra.getHFin().isAfter(reserva.getHInicio())) {
                    throw new IllegalArgumentException("Ya existe una reserva confirmada en ese horario");
                }
            }
        }

        if ("Confirmada".equals(dto.getEstado())) {
            reserva.setEstado("confirmada");
        } else {
            reserva.setEstado("rechazada");
        }
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

    //H10 - ED24 - Obtener el número de reservas de un cliente
    public Integer obtenerCantidadReservasPorCliente(Integer idUsuario) {
        Long cantidad = reservaRepositorio.contarReservasPorCliente(idUsuario);
        return cantidad.intValue();
    }

    // HU10 - ED25 - Obtener cantidad de próximas reservas de un cliente
    public Integer obtenerCantidadReservasProximas(Integer idUsuario) {
        Long cantidad = reservaRepositorio.contarReservasProximas(idUsuario);
        return cantidad.intValue();
    }

    // HU10 - ED26 - Obtener cantidad de reservas canceladas de un cliente
    public Integer obtenerCantidadReservasCanceladas(Integer idUsuario) {
        Long cantidad = reservaRepositorio.contarReservasCanceladas(idUsuario);
        return cantidad.intValue();
    }

    // HU10 - ED27 - Obtener próximas reservas de un cliente
    public List<ReservaDTO> obtenerProximasReservas(Integer idUsuario) {
        List<Reserva> reservas = reservaRepositorio.findByUsuario_IdUsuarioAndEstado(idUsuario, "confirmada");
        List<ReservaDTO> reservaDTOs = new ArrayList<>();
        for (Reserva reserva : reservas) {
            reservaDTOs.add(modelMapper.map(reserva, ReservaDTO.class));
        }
        return reservaDTOs;
    }

    // HU10 - ED28 - Obtener reservas solicitadas de un cliente
    public List<ReservaDTO> obtenerSolicitadasReservas(Integer idUsuario) {
        List<Reserva> reservas = reservaRepositorio.findByUsuario_IdUsuarioAndEstado(idUsuario, "solicitada");
        List<ReservaDTO> reservaDTOs = new ArrayList<>();
        for (Reserva reserva : reservas) {
            reservaDTOs.add(modelMapper.map(reserva, ReservaDTO.class));
        }
        return reservaDTOs;
    }

    // ED19: Cantidad total de reservas por deporte en un mes/anio (solo confirmadas y completadas)
    public List<DeporteParticipacionDTO> listarDeporteParticipacion() {
        Integer mes = LocalDate.now().getMonthValue();
        Integer anio = LocalDate.now().getYear();
        return reservaRepositorio.listarDeporteParticipacion(mes, anio);
    }


}