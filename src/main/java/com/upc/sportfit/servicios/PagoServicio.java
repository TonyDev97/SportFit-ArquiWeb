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

@Service
public class PagoServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    public List<Pago> listarPagos() {
        return pagoRepositorio.findAll();
    }

    // VALIDADO NICOLE
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        Pago pago = modelMapper.map(pagoDTO, Pago.class);
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

}