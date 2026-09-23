package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.servicios.PagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PagoControlador {

    @Autowired
    private PagoServicio pagoServicio;

    @GetMapping("/pagos")
    public List<Pago> listar() {
        return pagoServicio.listarPagos();
    }

    @PostMapping("/pago")
    public Pago registrar(@RequestBody Pago pago) {
        return pagoServicio.registrarPago(pago);
    }

    @GetMapping("/pago/{id}")
    public Pago listarId(@PathVariable("id") Long id) {
        return pagoServicio.listarPagoPorId(id);
    }

    @PutMapping("/pago-añadir")
    public Pago actualizar(@RequestBody Pago pago) {
        return pagoServicio.registrarPago(pago);
    }

    @DeleteMapping("/pago-eliminar/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        pagoServicio.eliminarPago(id);
    }
}