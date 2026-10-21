package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.PagoDTO;
import com.upc.sportfit.dtos.reportes.DetalleSedeDTO;
import com.upc.sportfit.dtos.reportes.DistribucionPagoDTO;
import com.upc.sportfit.dtos.reportes.IngresoDiarioDTO;
import com.upc.sportfit.dtos.reportes.MontoPerdidoDTO;
import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.repositorios.PagoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    //Verificar la reserva antes de registrar el pago.
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    public List<Pago> listarPagos() {
        return pagoRepositorio.findAll();
    }

    @Transactional

    // VALIDADO NICOLE
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        //Validaciones del pago.
        if (pagoDTO == null
                || pagoDTO.getReserva() == null
                || pagoDTO.getReserva().getIdReserva() == null) {

            throw new IllegalArgumentException(
                    "Debe indicar la reserva del pago"
            );
        }

        if (pagoDTO.getIdPago() != null) {
            throw new IllegalArgumentException(
                    "No debe enviar idPago al registrar un nuevo pago"
            );
        }

        if (pagoDTO.getMontoTotal() == null
                || pagoDTO.getMontoTotal().compareTo(BigDecimal.ZERO) <= 0
                || pagoDTO.getMontoTotal().stripTrailingZeros().scale() > 2) {

            throw new IllegalArgumentException(
                    "El monto debe ser positivo y tener hasta dos decimales"
            );
        }

        if (pagoDTO.getMetodo() == null
                || pagoDTO.getMetodo().trim().isEmpty()
                || pagoDTO.getMetodo().length() > 50) {

            throw new IllegalArgumentException(
                    "Debe indicar un método de pago de hasta 50 caracteres"
            );
        }

        if (pagoDTO.getUrlComprobante() == null
                || pagoDTO.getUrlComprobante().trim().isEmpty()
                || pagoDTO.getUrlComprobante().length() > 500) {

            throw new IllegalArgumentException(
                    "Debe indicar un comprobante de hasta 500 caracteres"
            );
        }

        Reserva reservaValidada = reservaRepositorio
                .findById(pagoDTO.getReserva().getIdReserva())
                .orElseThrow(() ->
                        new NoSuchElementException("La reserva no existe")
                );

        //Evitar registrar otro pago para la misma reserva.
        if (!pagoRepositorio.findByReserva_IdReserva(
                reservaValidada.getIdReserva()).isEmpty()) {

            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "La reserva ya tiene un pago registrado"
            );
        }

        if (!"solicitada".equalsIgnoreCase(reservaValidada.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se puede registrar el pago de una reserva solicitada"
            );
        }

        //Verificar el importe antes de guardar.
        validarMontoReserva(
                reservaValidada,
                pagoDTO.getMontoTotal()
        );

        Pago pago = modelMapper.map(pagoDTO, Pago.class);
        //Asociar la reserva verificada.
        pago.setReserva(reservaValidada);
        pago = pagoRepositorio.save(pago);
        return modelMapper.map(pago, PagoDTO.class);

    }

    public Pago listarPagoPorId(Integer id) {
        return pagoRepositorio.findById(id).orElse(new Pago());
    }

    public void eliminarPago(Integer id) {
        pagoRepositorio.deleteById(id);
    }

    // HU07 - ED20: Reporte financiero por período (con mes y año)
    public List<IngresoDiarioDTO> obtenerIngresosPorPeriodo(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        List<IngresoDiarioDTO> ingresos = pagoRepositorio.obtenerIngresosPorPeriodo(rango[0], rango[1]);
        if (ingresos.isEmpty()) {
            throw new RuntimeException("No se encontraron ingresos para el período seleccionado");
        }
        return ingresos;
    }

    // HU07 - ED83: Distribución por método de pago
    public List<DistribucionPagoDTO> obtenerDistribucionPorMetodoPago(Integer mes, Integer anio) {
        LocalDate[] rango = validarYCalcularRangoFechas(mes, anio);
        return pagoRepositorio.obtenerDistribucionPorMetodoPago(rango[0], rango[1]);
    }

    // Método privado para validar y calcular rango de fechas
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


    // HU08 - ED22: Monto perdido por cancelaciones (desde Pago, sin tocar Reserva)
    public MontoPerdidoDTO obtenerMontoPerdidoPorCancelaciones(LocalDate fechaInicio, LocalDate fechaFin) {
        Number monto = pagoRepositorio.obtenerMontoPerdidoPorCancelaciones(fechaInicio, fechaFin);
        BigDecimal valor;
        if (monto != null) {
            valor = new BigDecimal(monto.toString());
        } else {
            valor = BigDecimal.ZERO;
        }
        return new MontoPerdidoDTO(valor);
    }

    public List<DetalleSedeDTO> listarDetalleSede(Integer mes, Integer anio, Integer sedeId) {
        return pagoRepositorio.listarDetalleSede(mes, anio, sedeId);
    }

    //Validar el importe según precio por hora y duración.
    public void validarMontoReserva(
            Reserva reserva,
            BigDecimal montoRecibido) {

        if (reserva == null
                || reserva.getSedeCancha() == null
                || reserva.getSedeCancha().getPrecio() == null
                || reserva.getHInicio() == null
                || reserva.getHFin() == null) {

            throw new IllegalArgumentException(
                    "La reserva no tiene los datos necesarios para calcular el importe"
            );
        }

        if (!reserva.getHInicio().isBefore(reserva.getHFin())) {
            throw new IllegalArgumentException(
                    "Las horas de la reserva no son válidas"
            );
        }

        BigDecimal precioHora = reserva.getSedeCancha().getPrecio();

        if (precioHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El precio por hora de la cancha debe ser positivo"
            );
        }

        long segundos = java.time.Duration.between(
                reserva.getHInicio(),
                reserva.getHFin()
        ).getSeconds();

        BigDecimal montoEsperado = precioHora
                .multiply(BigDecimal.valueOf(segundos))
                .divide(
                        BigDecimal.valueOf(3600),
                        2,
                        java.math.RoundingMode.HALF_UP
                );

        if (montoRecibido == null
                || montoRecibido.compareTo(montoEsperado) != 0) {

            throw new IllegalArgumentException(
                    "El monto debe ser S/ "
                            + montoEsperado.toPlainString()
                            + " según el precio por hora y la duración"
            );
        }
    }

}