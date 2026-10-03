package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.PagoDTO;
import com.upc.sportfit.dtos.reportes.IngresoDiarioDTO;
import com.upc.sportfit.entidades.Pago;
import com.upc.sportfit.servicios.PagoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    //VALIDADO NICOLE ED03
    @PostMapping("/pago")
    public ResponseEntity<PagoDTO> registrar(@RequestBody PagoDTO pagoDTO) {
        PagoDTO nuevoPago = pagoServicio.registrarPago(pagoDTO);
        return ResponseEntity.ok(nuevoPago);
    }

    @GetMapping("/pago/{id}")
    public Pago listarId(@PathVariable("id") Integer id) {
        return pagoServicio.listarPagoPorId(id);
    }

    @PutMapping("/pago-añadir")
    public PagoDTO actualizar(@RequestBody PagoDTO pago) {
        return pagoServicio.registrarPago(pago);
    }

    @DeleteMapping("/pago-eliminar/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        pagoServicio.eliminarPago(id);
    }

    //esta
    // HU07 - ED20: Reporte financiero por período
    @GetMapping("/pago/reporte/ingresos")
    public ResponseEntity<List<IngresoDiarioDTO>> obtenerIngresosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(pagoServicio.obtenerIngresosPorPeriodo(fechaInicio, fechaFin));
    }




}