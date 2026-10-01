package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.reportes.*;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upc.sportfit.dtos.EstadoReservaDTO;
import com.upc.sportfit.dtos.SolicitudReservaDTO;

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
    public List<Reserva> listarReservas(){
        return reservaServicio.listarReservas();
    }

    //VALIDADO
    // GET /api/reserva/disponibilidad/{idCancha}?fecha=2026-09-15
    @GetMapping("/reserva/disponibilidad/{idCancha}")
    public List<Reserva> listarReservaCancha(@PathVariable Integer idCancha,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return reservaServicio.listarReservaCancha(fecha, idCancha);
    }

    @GetMapping("/reserva/usuario/{idUsuario}")
    public List<ReservaDTO> listarReservasCliente(@PathVariable Integer idUsuario){
        return reservaServicio.listarReservasCliente(idUsuario);
    }

    @GetMapping("/reserva-validar")
    public List<Reserva> listarReservaConfirmada(){
        return reservaServicio.listarReservaConfirmada();
    }

    @PostMapping("/reserva")
    public Reserva registrarReserva(@RequestBody Reserva reserva){
        return reservaServicio.registrarReserva(reserva);
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
    public List<Reserva> listarReservasSede(@PathVariable Integer id_sede){
        return reservaServicio.listarReservasSede(id_sede);
    }
    //VALIDADO
    @GetMapping("reservas/deporte/{deporte}")
    public List<Reserva> listarReservasDeporte(@PathVariable String deporte){
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

    // HU08 - ED21: Cancelaciones por día (mes y año como parámetros)
    @GetMapping("/reservas-canceladas/dia")
    public ResponseEntity<List<CancelacionDiaDTO>> obtenerCancelacionesPorDia(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerCancelacionesPorDia(mes, anio));
    }

    // HU08 - ED22: Monto perdido por cancelaciones
    @GetMapping("/reservas-canceladas/monto-perdido")
    public ResponseEntity<MontoPerdidoDTO> obtenerMontoPerdidoPorCancelaciones(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerMontoPerdidoPorCancelaciones(mes, anio));
    }

    // HU08 - ED23: Total de cancelaciones
    @GetMapping("/reservas-canceladas/total")
    public ResponseEntity<TotalCancelacionesDTO> obtenerTotalCancelaciones(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerTotalCancelaciones(mes, anio));
    }

    // HU09 - ED24: Reservas por cliente (general)
    @GetMapping("/reservas-cliente")
    public ResponseEntity<List<ReservasPorClienteDTO>> obtenerReservasPorCliente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reservaServicio.obtenerReservasPorCliente(fechaInicio, fechaFin));
    }

    // HU09 - ED25: Reservas por cliente con filtro mínimo
    @GetMapping("/reservas/cliente/minimo")
    public ResponseEntity<List<ReservasPorClienteDTO>> obtenerReservasPorClienteMinimo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam Long minReservas) {
        return ResponseEntity.ok(reservaServicio.obtenerReservasPorClienteMinimo(fechaInicio, fechaFin, minReservas));
    }

    // HU09 - ED26: Top de clientes con más reservas
    @GetMapping("/reservas/cliente/top")
    public ResponseEntity<List<ReservasPorClienteDTO>> obtenerTopClientesReservas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam Integer top) {
        return ResponseEntity.ok(reservaServicio.obtenerTopClientesReservas(fechaInicio, fechaFin, top));
    }

    // HU12 - Listar Reservas DTO por estado opcional
    @GetMapping("/reservas/dto")
    public ResponseEntity<List<ReservaDTO>> listarReservasDTO(
            @RequestParam(required = false) String estado) {

        return ResponseEntity.ok(
                reservaServicio.listarReservasDTO(estado)
        );
    }

    // HU13 - Consultar Detalle de Solicitud de Reserva y Pagos
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<SolicitudReservaDTO> consultarSolicitud(
            @PathVariable Integer idReserva) {

        return ResponseEntity.ok(
                reservaServicio.consultarSolicitud(idReserva)
        );
    }

    // HU13 - Cambiar Estado de la Solicitud (Aceptar / Rechazar)
    @PutMapping("/reservas/{id_reserva}/estado")
    public ResponseEntity<ReservaDTO> cambiarEstadoSolicitud(
            @PathVariable("id_reserva") Integer idReserva,
            @RequestBody EstadoReservaDTO dto) {

        return ResponseEntity.ok(
                reservaServicio.cambiarEstadoSolicitud(idReserva, dto)
        );
    }

    // HU14 - Listar Reservas por Cancha
    @GetMapping("/reservas/cancha/{idCancha}")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorCancha(
            @PathVariable Integer idCancha) {

        return ResponseEntity.ok(
                reservaServicio.listarReservasPorCancha(idCancha)
        );
    }



}