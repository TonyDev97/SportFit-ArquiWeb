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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PagoControlador {

    @Autowired
    private PagoServicio pagoServicio;

    // ED20
    @GetMapping("/pagos-detalles/reserva/sede")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<DetalleSedeDTO> listarDetalleSede(@RequestParam(name = "mes", required = true) Integer mes,
                                                  @RequestParam(name = "anio", required = true) Integer anio,
                                                  @RequestParam(name = "sedeId", required = false) Integer sedeId)  {
        return pagoServicio.listarDetalleSede(mes, anio, sedeId);
    }

    // ED41 - HU07 : Reporte de Ingresos (gráfico lineal + tarjetas métricas)
    @GetMapping("/pago/reporte/ingresos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IngresoDiarioDTO>> obtenerIngresosPorPeriodo(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(pagoServicio.obtenerIngresosPorPeriodo(mes, anio));
    }

    // ED42 - HU07: Distribución por Método de Pago (gráfico circular)
    @GetMapping("/pago/reporte/metodos-pago")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<DistribucionPagoDTO>> obtenerDistribucionPorMetodoPago(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(pagoServicio.obtenerDistribucionPorMetodoPago(mes, anio));
    }

    // ED44
    @PostMapping("/pago")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<PagoDTO> registrar(@RequestBody PagoDTO pagoDTO) {
        PagoDTO nuevoPago = pagoServicio.registrarPago(pagoDTO);
        return ResponseEntity.ok(nuevoPago);
    }

    /*
    @GetMapping("/pagos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Pago> listar() {
        return pagoServicio.listarPagos();
    }


    @GetMapping("/pago/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public Pago listarId(@PathVariable("id") Integer id) {
        return pagoServicio.listarPagoPorId(id);
    }

    @PutMapping("/pago-añadir")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public PagoDTO actualizar(@RequestBody PagoDTO pago) {
        return pagoServicio.registrarPago(pago);
    }

    @DeleteMapping("/pago-eliminar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@PathVariable("id") Integer id) {
        pagoServicio.eliminarPago(id);
    }

*/

}