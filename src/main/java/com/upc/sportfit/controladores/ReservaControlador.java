package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.ReservaDTO;
import com.upc.sportfit.dtos.ReservaHorarioDTO;
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
    //VALIDADO NICOLE ED79
    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaDTO>> listarReservas(){
        return ResponseEntity.ok(reservaServicio.listarReservas());
    }

    //VALIDADO NICOLE
    @GetMapping("/reservas/horarios")
    public ResponseEntity<List<ReservaHorarioDTO>> ListarReservasPorFechaDeporteSede(@RequestParam LocalDate fecha, @RequestParam Integer idDeporte, @RequestParam Integer idSede) {
        return ResponseEntity.ok(reservaServicio.ListarReservasPorFechaDeporteSede(fecha, idDeporte, idSede));
    }

    //VALIDADO NICOLE ED72
    @PutMapping("/reserva/estado/{id}")
    public ResponseEntity<ReservaDTO> ActualizarEstado(@PathVariable("id") Integer id, @RequestParam("estado") String estado) {
        ReservaDTO reservaActualizada = reservaServicio.ActualizarEstado(id, estado);
        return ResponseEntity.ok(reservaActualizada);
    }

    @GetMapping("/reserva/usuario/{idUsuario}")
    public List<ReservaDTO> listarReservasCliente(@PathVariable Integer idUsuario){
        return reservaServicio.listarReservasCliente(idUsuario);
    }

    @GetMapping("/reserva-validar")
    public List<Reserva> listarReservaConfirmada(){
        return reservaServicio.listarReservaConfirmada();
    }

    //VALIDADO
    @PostMapping("/reserva")
    public ResponseEntity<ReservaDTO> registrarReserva(@RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(reservaServicio.registrarReserva(reservaDTO));
    }

    //VALIDADO
    @PutMapping("/reserva-actualizar")
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody ReservaDTO reservaDTO){
        return ResponseEntity.ok(reservaServicio.editarReserva(reservaDTO));
    }

     //VALIDADO NICOLE ED13
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
    public ResponseEntity<List<ReservaDTO>> listarReservasSede(@PathVariable Integer id_sede){
        return ResponseEntity.ok(reservaServicio.listarReservasSede(id_sede));
    }
    //VALIDADO
    @GetMapping("reservas/deporte/{deporte}")
    public ResponseEntity<List<ReservaDTO>> listarReservasDeporte(@PathVariable String deporte){
        return ResponseEntity.ok(reservaServicio.listarReservasDeporte(deporte));
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
    @GetMapping("/reservas-canceladas/periodo")
    public ResponseEntity<List<CancelacionDiaDTO>> obtenerCancelacionesPorPeriodo(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerCancelacionesPorPeriodo(mes, anio));
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

    // HU08 - ED84: Distribución por motivo de cancelación (Gráfico de Barras)
    @GetMapping("/reservas-canceladas/motivos")
    public ResponseEntity<List<MotivoCancelacionDTO>> obtenerDistribucionPorMotivoCancelacion(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerDistribucionPorMotivoCancelacion(mes, anio));
    }

    // HU08 - ED85: Tendencia anual de cancelaciones (Gráfico de Líneas)
    @GetMapping("/reservas-canceladas/tendencia-anual")
    public ResponseEntity<List<TendenciaCancelacionDTO>> obtenerTendenciaAnualCancelaciones(
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerTendenciaAnualCancelaciones(anio));
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