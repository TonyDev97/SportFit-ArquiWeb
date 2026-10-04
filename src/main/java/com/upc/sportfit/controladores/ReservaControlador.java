package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.*;
import com.upc.sportfit.dtos.reportes.*;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    // ED01:  Consultar Detalle de Solicitud de Reserva y Pagos
    @GetMapping("/reserva/{idReserva}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<SolicitudReservaDTO> consultarSolicitud(
            @PathVariable Integer idReserva) {

        return ResponseEntity.ok(
                reservaServicio.consultarSolicitud(idReserva)
        );
    }

    //ED02 - HU4 HU10 - Obtener reservas del cliente
    @GetMapping("/reserva/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<ReservaDTO> listarReservasCliente(@PathVariable Integer idUsuario){
        return reservaServicio.listarReservasCliente(idUsuario);
    }

    // ED03
    @GetMapping("/reservas/sede/tipoCancha")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDeporteSedeDTO>> frecuenciaReservasPorDeporteSede(
            @RequestParam (required = false) Integer mes
    ){
        return ResponseEntity.ok(reservaServicio.frecuenciaReservasPorDeporteSede(mes));
    }

    // ED04: Cantidad total de reservas por deporte en un mes/anio (solo confirmadas y completadas)
    @GetMapping("/reservas/sede/tipoCancha/participacion")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<DeporteParticipacionDTO> listarDeporteParticipacion(){
        return reservaServicio.listarDeporteParticipacion();
    }

    // ED05 - HU08: Cancelaciones por día (mes y año como parámetros)
    @GetMapping("/reservas-canceladas/periodo")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<CancelacionDiaDTO>> obtenerCancelacionesPorPeriodo(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerCancelacionesPorPeriodo(mes, anio));
    }

    // ED06 - HU08: Monto perdido por cancelaciones
    @GetMapping("/reservas-canceladas/monto-perdido")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<MontoPerdidoDTO> obtenerMontoPerdidoPorCancelaciones(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerMontoPerdidoPorCancelaciones(mes, anio));
    }

    // ED07 - HU08: Total de cancelaciones
    @GetMapping("/reservas-canceladas/total")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<TotalCancelacionesDTO> obtenerTotalCancelaciones(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerTotalCancelaciones(mes, anio));
    }

    // ED08 - HU10 - Obtener número de reservas de un cliente cliente (dashboard - tarjeta) - Dilan
    @GetMapping("/reservas-cantidad/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Integer> obtenerCantidadReservasPorCliente(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasPorCliente(idUsuario));
    }

    // HU09 - ED25 - Obtener cantidad de próximas reservas de un cliente
    @GetMapping("/reservas-cantidad-proximas/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Integer> obtenerCantidadReservasProximasPorCliente(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasProximas(idUsuario));
    }

    //Se puede juntar ED25 con ED26 solo mandando el estado y el usuario
    // ED10 - HU10 - Obtener cantidad de reservas canceladas de un cliente
    @GetMapping("/reservas-cantidad-canceladas/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Integer> obtenerCantidadReservasCanceladas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerCantidadReservasCanceladas(idUsuario));
    }

    // ED11 - HU10 - Obtener proximas reservas de un cliente (dashboard - mostrar) - Dilan
    @GetMapping("/reservas-proximas/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> obtenerProximasReservas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerProximasReservas(idUsuario));
    }

    // ED12 - HU10 - Obtener las reservas solicitadas de un cliente (dashboard - mostrar) - Dilan
    @GetMapping("/reservas-solicitadas/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> obtenerSolicitadasReservas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaServicio.obtenerSolicitadasReservas(idUsuario));
    }

    // ED13: Tendencia anual de reservas por deporte y mes
    @GetMapping("/reservas-tendencia-anual")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<TendenciaAnualDTO> listarTendenciaAnual(@RequestParam (required = false) Integer anio){
        return reservaServicio.listarTendenciaAnual(anio);
    }

    // ED14
    @GetMapping("/reservas-banners")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public BannerReservasDTO cargarBannerReservas(){
        return reservaServicio.cargarBannerReservas();
    }

    // ED15 - HU14 - Listar Reservas por Cancha
    @GetMapping("/reservas/cancha/{idCancha}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorCancha(
            @PathVariable Integer idCancha) {

        return ResponseEntity.ok(
                reservaServicio.listarReservasPorCancha(idCancha)
        );
    }

    // ED16 VALIDADO NICOLE
    @GetMapping("/reservas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> listarReservas(){
        return ResponseEntity.ok(reservaServicio.listarReservas());
    }

    // ED17 VALIDADO NICOLE
    @GetMapping("/reservas/horarios")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaHorarioDTO>> ListarReservasPorFechaDeporteSede(@RequestParam LocalDate fecha, @RequestParam Integer idDeporte, @RequestParam Integer idSede) {
        return ResponseEntity.ok(reservaServicio.ListarReservasPorFechaDeporteSede(fecha, idDeporte, idSede));
    }

    // ED18 - HU08: Distribución por motivo de cancelación (Gráfico de Barras)
    @GetMapping("/reservas-canceladas/motivos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<MotivoCancelacionDTO>> obtenerDistribucionPorMotivoCancelacion(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerDistribucionPorMotivoCancelacion(mes, anio));
    }

    // ED19 - HU08: Tendencia anual de cancelaciones (Gráfico de Líneas)
    @GetMapping("/reservas-canceladas/tendencia-anual")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<TendenciaCancelacionDTO>> obtenerTendenciaAnualCancelaciones(
            @RequestParam(required = true) Integer anio) {
        return ResponseEntity.ok(reservaServicio.obtenerTendenciaAnualCancelaciones(anio));
    }

    // ED21 - HU09: Reporte consolidado de reservas por cliente
    @GetMapping("/reportes/reservas-por-cliente")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaClienteDTO>> obtenerReservasPorClienteConsolidado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) Integer minReservas,
            @RequestParam(required = false) Integer maxReservas,
            @RequestParam(required = false) Integer top) {
        return ResponseEntity.ok(reservaServicio.obtenerReservasPorClienteConsolidado(
                fechaDesde, fechaHasta, minReservas, maxReservas, top));
    }

    // ED22 VALIDADO
    @PostMapping("/reserva")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> registrarReserva(@RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(reservaServicio.registrarReserva(reservaDTO));
    }

    // ED23 VALIDADO
    @PutMapping("/reserva-actualizar")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody ReservaDTO reservaDTO){
        return ResponseEntity.ok(reservaServicio.editarReserva(reservaDTO));
    }

    // ED24 VALIDADO NICOLE
    @PutMapping("/reserva-cancelar/{idReserva}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> eliminarLogicoReserva(@PathVariable Integer idReserva){
        return ResponseEntity.ok(reservaServicio.eliminarLogicoReserva(idReserva));
    }

    // ED25 VALIDADO NICOLE
    @PutMapping("/reserva/estado/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> ActualizarEstado(@PathVariable("id") Integer id, @RequestParam("estado") String estado) {
        ReservaDTO reservaActualizada = reservaServicio.ActualizarEstado(id, estado);
        return ResponseEntity.ok(reservaActualizada);
    }


    /*
    @GetMapping("/reserva-validar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Reserva> listarReservaConfirmada(){
        return reservaServicio.listarReservaConfirmada();
    }

    @DeleteMapping("/reserva-eliminar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminarReserva(@RequestBody Integer id){
        reservaServicio.eliminarReserva(id);
    }

    // usan?
    @GetMapping("reservas/sede/{id_sede}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> listarReservasSede(@PathVariable Integer id_sede){
        return ResponseEntity.ok(reservaServicio.listarReservasSede(id_sede));
    }
    // no uso
    @GetMapping("reservas/deporte/{deporte}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> listarReservasDeporte(@PathVariable String deporte){
        return ResponseEntity.ok(reservaServicio.listarReservasDeporte(deporte));
    }

    // HU13: Listar reservas con filtro opcional por estado.
    @GetMapping("/reservas/dto")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReservaDTO>> listarReservasDTO(
            @RequestParam(required = false) String estado) {

        return ResponseEntity.ok(
                reservaServicio.listarReservasDTO(estado)
        );
    }

    // HU13 - Cambiar Estado de reserva (Aceptar / Rechazar)
    @PutMapping("/reservas/{id_reserva}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReservaDTO> cambiarEstadoSolicitud(
            @PathVariable("id_reserva") Integer idReserva,
            @RequestBody EstadoReservaDTO dto) {

        return ResponseEntity.ok(
                reservaServicio.cambiarEstadoSolicitud(idReserva, dto)
        );
    }


    */

}