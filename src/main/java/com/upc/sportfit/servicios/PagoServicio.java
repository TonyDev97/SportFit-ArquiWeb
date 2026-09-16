package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.repositorios.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Pago listarPagoPorId(Long id) {
        return pagoRepositorio.findById(id).orElse(new Pago());
    }

    public void eliminarPago(Long id) {
        pagoRepositorio.deleteById(id);
    }
}