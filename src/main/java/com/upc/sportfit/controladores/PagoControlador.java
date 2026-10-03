package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.PagoDTO;
import com.upc.sportfit.dtos.reportes.DetalleSedeDTO;
import com.upc.sportfit.dtos.reportes.DistribucionPagoDTO;
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

    // HU07 - ED20: Reporte de Ingresos (gráfico lineal + tarjetas métricas)
    @GetMapping("/pago/reporte/ingresos")
    public ResponseEntity<List<IngresoDiarioDTO>> obtenerIngresosPorPeriodo(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(pagoServicio.obtenerIngresosPorPeriodo(mes, anio));
    }

    // HU07 - ED83: Distribución por Método de Pago (gráfico circular)
    @GetMapping("/pago/reporte/metodos-pago")
    public ResponseEntity<List<DistribucionPagoDTO>> obtenerDistribucionPorMetodoPago(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(pagoServicio.obtenerDistribucionPorMetodoPago(mes, anio));
    }

    // ED 86
    @GetMapping("/pagos-detalles/reserva/sede")
    public List<DetalleSedeDTO> listarDetalleSede(@RequestParam(name = "mes", required = true) Integer mes,
                                                  @RequestParam(name = "anio", required = true) Integer anio,
                                                  @RequestParam(name = "sedeId", required = false) Integer sedeId)  {
        return pagoServicio.listarDetalleSede(mes, anio, sedeId);
    }


}