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

    //VALIDADO NICOLE ED81
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

    //ED16 - HU4 HU10 - Obtener reservas del cliente
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

    // usan?
    @GetMapping("reservas/sede/{id_sede}")
    public ResponseEntity<List<ReservaDTO>> listarReservasSede(@PathVariable Integer id_sede){
        return ResponseEntity.ok(reservaServicio.listarReservasSede(id_sede));
    }
    // no uso
    @GetMapping("reservas/deporte/{deporte}")
    public ResponseEntity<List<ReservaDTO>> listarReservasDeporte(@PathVariable String deporte){
        return ResponseEntity.ok(reservaServicio.listarReservasDeporte(deporte));
    }

    // ED18
    @GetMapping("/reservas/sede/tipoCancha")
    public ResponseEntity<List<ReservaDeporteSedeDTO>> frecuenciaReservasPorDeporteSede(
            @RequestParam (required = false) Integer mes
    ){
        return ResponseEntity.ok(reservaServicio.frecuenciaReservasPorDeporteSede(mes));
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


    // HU09 - ED87: Reporte consolidado de reservas por cliente
    @GetMapping("/reportes/reservas-por-cliente")
    public ResponseEntity<List<ReservaClienteDTO>> obtenerReservasPorClienteConsolidado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) Integer minReservas,
            @RequestParam(required = false) Integer maxReservas,
            @RequestParam(required = false) Integer top) {
        return ResponseEntity.ok(reservaServicio.obtenerReservasPorClienteConsolidado(
                fechaDesde, fechaHasta, minReservas, maxReservas, top));
    }


    // HU13: Listar reservas con filtro opcional por estado.
    @GetMapping("/reservas/dto")
    public ResponseEntity<List<ReservaDTO>> listarReservasDTO(
            @RequestParam(required = false) String estado) {

        return ResponseEntity.ok(
                reservaServicio.listarReservasDTO(estado)
        );
    }

    // ED11:  Consultar Detalle de Solicitud de Reserva y Pagos
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<SolicitudReservaDTO> consultarSolicitud(
            @PathVariable Integer idReserva) {

        return ResponseEntity.ok(
                reservaServicio.consultarSolicitud(idReserva)
        );
    }

    // HU13 - Cambiar Estado de reserva (Aceptar / Rechazar)
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

    //HU10 - ED24 - Obtener número de reservas de un cliente cliente (dashboard - tarjeta) - Dilan
    @GetMapping("/reservas-cantidad/usuario/{idUsuario}")
    public ResponseEntity<Integer> obtenerCantidadReservasPorCliente(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasPorCliente(idUsuario));
    }

    // HU10 - ED25 - Obtener cantidad de próximas reservas de un cliente
    @GetMapping("/reservas-cantidad-proximas/usuario/{idUsuario}")
    public ResponseEntity<Integer> obtenerCantidadReservasProximasPorCliente(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasProximas(idUsuario));
    }
    //Se puede juntar ED25 con ED26 solo mandando el estado y el usuario
    // HU10 - ED26 - Obtener cantidad de reservas canceladas de un cliente
    @GetMapping("/reservas-cantidad-canceladas/usuario/{idUsuario}")
    public ResponseEntity<Integer> obtenerCantidadReservasCanceladas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasCanceladas(idUsuario));
    }

    //HU10 - ED27 - Obtener proximas reservas de un cliente (dashboard - mostrar) - Dilan
    @GetMapping("/reservas-proximas/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaDTO>> obtenerProximasReservas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerProximasReservas(idUsuario));
    }

    //HU10 - ED28 - Obtener las reservas solicitadas de un cliente (dashboard - mostrar) - Dilan
    @GetMapping("/reservas-solicitadas/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaDTO>> obtenerSolicitadasReservas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerSolicitadasReservas(idUsuario));
    }

    // ED19: Cantidad total de reservas por deporte en un mes/anio (solo confirmadas y completadas)
    @GetMapping("/reservas/sede/tipoCancha/participacion")
    public List<DeporteParticipacionDTO> listarDeporteParticipacion(){
        return reservaServicio.listarDeporteParticipacion();
    }

    // ED29: Tendencia anual de reservas por deporte y mes
    @GetMapping("/reservas-tendencia-anual/{anio}")
    public List<TendenciaAnualDTO> listarTendenciaAnual(@PathVariable Integer anio){
        return reservaServicio.listarTendenciaAnual(anio);
    }

    // ED30

}