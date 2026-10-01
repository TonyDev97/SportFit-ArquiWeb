package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.reportes.IngresoDiarioDTO;
import com.upc.sportfit.dtos.reportes.MontoPerdidoDTO;
import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.repositorios.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PagoServicio {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    public List<Pago> listarPagos() {
        return pagoRepositorio.findAll();
    }

    public Pago registrarPago(Pago pago) {
        return pagoRepositorio.save(pago);
    }

    public Pago listarPagoPorId(Integer id) {
        return pagoRepositorio.findById(id).orElse(new Pago());
    }

    public void eliminarPago(Integer id) {
        pagoRepositorio.deleteById(id);
    }

    // HU07 - ED20: Reporte financiero por período
    public List<IngresoDiarioDTO> obtenerIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<IngresoDiarioDTO> ingresos = pagoRepositorio.obtenerIngresosPorPeriodo(fechaInicio, fechaFin);
        if (ingresos.isEmpty()) {
            throw new RuntimeException("No se encontraron ingresos para el período seleccionado");
        }
        return ingresos;
    }

    // HU08 - ED22: Monto perdido por cancelaciones (desde Pago, sin tocar Reserva)
    public MontoPerdidoDTO obtenerMontoPerdidoPorCancelaciones(Integer mes, Integer anio) {
        Number monto = pagoRepositorio.obtenerMontoPerdidoPorCancelaciones(mes, anio);
        BigDecimal valor;
        if (monto != null) {
            valor = new BigDecimal(monto.toString());
        } else {
            valor = BigDecimal.ZERO;
        }
        return new MontoPerdidoDTO(valor);
    }




}