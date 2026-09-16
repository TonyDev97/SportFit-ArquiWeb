package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.servicios.PagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoControlador {

    @Autowired
    private PagoServicio pagoServicio;

    @GetMapping
    public List<Pago> listar() {
        return pagoServicio.listarPagos();
    }

    @PostMapping
    public Pago registrar(@RequestBody Pago pago) {
        return pagoServicio.registrarPago(pago);
    }

    @GetMapping("/{id}")
    public Pago listarId(@PathVariable("id") Long id) {
        return pagoServicio.listarPagoPorId(id);
    }

    @PutMapping
    public Pago actualizar(@RequestBody Pago pago) {
        return pagoServicio.registrarPago(pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        pagoServicio.eliminarPago(id);
    }
}