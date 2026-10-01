package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.reportes.ReservaDeporteSede;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;
    //VALIDADO
    @GetMapping("/reservas")
    public List<ReservaDTO> listarReservas(){

        return reservaServicio.listarReservas();
    }

    //VALIDADO
    // GET /api/reserva/disponibilidad/{idCancha}?fecha=2026-09-15
    @GetMapping("/reserva/disponibilidad/{idCancha}")
    public List<ReservaDTO> listarReservaCancha(@PathVariable Integer idCancha,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return reservaServicio.listarReservaCancha(fecha, idCancha);
    }

    @GetMapping("/reserva/usuario/{idUsuario}")
    public List<Reserva> listarReservasCliente(@PathVariable Integer idUsuario){
        return reservaServicio.listarReservasCliente(idUsuario);
    }

    @GetMapping("/reserva-validar")
    public List<Reserva> listarReservaConfirmada(){
        return reservaServicio.listarReservaConfirmada();
    }

    @PostMapping("/reserva")
    public ReservaDTO registrarReserva(@RequestBody ReservaDTO reservaDTO){
        return reservaServicio.registrarReserva(reservaDTO);
    }

    //VALIDADO
    @PutMapping("/reserva-actualizar")
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody ReservaDTO reservaDTO){
        return ResponseEntity.ok(reservaServicio.editarReserva(reservaDTO));
    }

     //VALIDADO
    @PutMapping("/reserva-cancelar/{idReserva}")
    public ResponseEntity<ReservaDTO> eliminarLogicoReserva(@PathVariable Integer idReserva){
        return ResponseEntity.ok(reservaServicio.eliminarLogicoReserva(idReserva));
    }

    @DeleteMapping("/reserva-eliminar/{id}")
    public void eliminarReserva(@RequestBody Integer id){
        reservaServicio.eliminarReserva(id);
    }

    //VALIDADO
    @GetMapping("reservas/sede/{id_sede}")
    public List<ReservaDTO> listarReservasSede(@PathVariable Integer id_sede){
        return reservaServicio.listarReservasSede(id_sede);
    }
    //VALIDADO
    @GetMapping("reservas/deporte/{deporte}")
    public List<ReservaDTO> listarReservasDeporte(@PathVariable String deporte){
        return reservaServicio.listarReservasDeporte(deporte);
    }

    // Validado
    @GetMapping("/reservas/sede/tipoCancha")
    public ResponseEntity<List<ReservaDeporteSede>> frecuenciaReservasPorDeporteSede(){
        return ResponseEntity.ok(reservaServicio.frecuenciaReservasPorDeporteSede());
    }

    // Validado
    @GetMapping("/reservas/sede/tipoCancha/{fechaMin}{fechaMax}")
    public ResponseEntity<List<ReservaDeporteSede>> frecuenciaReservasPorDeporteSedeEntreFechas(@PathVariable LocalDate fechaMin, @PathVariable LocalDate fechaMax){
        return ResponseEntity.ok(reservaServicio.frecuenciaReservasPorDeporteSedeEntreFechas(fechaMin, fechaMax));
    }


}