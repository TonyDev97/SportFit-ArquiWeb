package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.IncidenciaRegistroDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.BannerIncidenciasDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.DistribucionCategoriaDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.EstadoResolucionDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.TendenciaIncidenciasDTO;
import com.upc.sportfit.dtos.IncidenciaRespuestaDTO;
import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.servicios.IncidenciaServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.List;
import com.upc.sportfit.dtos.IncidenciaDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class IncidenciaControlador {
    @Autowired
    private IncidenciaServicio incidenciaServicio;

    //ED27: Consultar el historial de incidencias de un cliente.
    @GetMapping("/incidencia/usuario/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<IncidenciaDTO> lista = incidenciaServicio.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }

    //ED28: Filtrar incidencias del cliente por tipo y fecha
    @GetMapping("/incidencia/usuario/{idUsuario}/filtro")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> filtrarIncidenciasPorUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<IncidenciaDTO> lista = incidenciaServicio.filtrarPorUsuarioTipoYFecha(idUsuario, tipo, fecha);
        return ResponseEntity.ok(lista);
    }

    //ED29: Consultar todas las incidencias.
    @GetMapping("/incidencias")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> listar() {
        List<IncidenciaDTO> lista = incidenciaServicio.listarIncidencias();
        return ResponseEntity.ok(lista);
    }

    //ED30: Consultar una incidencia por ID.
    @GetMapping("/incidencia-id/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<IncidenciaDTO> buscarIncidenciaPorId(@PathVariable Integer id) {
        IncidenciaDTO incidenciaDTO = incidenciaServicio.buscarIncidenciaPorId(id);
        return ResponseEntity.ok(incidenciaDTO);
    }

    //ED33: Filtrar incidencias del administrador por tipo, fecha y/o estado.
    @GetMapping("/incidencias/filtro")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> filtrarAdministrador(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        List<IncidenciaDTO> lista = incidenciaServicio.filtrarParaAdministrador(tipo, estado, fecha);
        return ResponseEntity.ok(lista);
    }

    //ED34: Registrar incidencia
    @PostMapping("/incidencia")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<IncidenciaDTO> registrarIncidencia(@Valid @RequestBody IncidenciaRegistroDTO registroDTO) {
        return ResponseEntity.ok(incidenciaServicio.registrarIncidencia(registroDTO));
    }

    //ED35: Responder Incidencia pendiente
    @PutMapping("/incidencia-responder")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<IncidenciaDTO> responderIncidencia(@Valid @RequestBody IncidenciaRespuestaDTO respuestaDTO) {
        return ResponseEntity.ok(incidenciaServicio.responderIncidencia(respuestaDTO));
    }

    //REPORTE INCIDENCIAS
    //ED31
    @GetMapping("/incidencias/banner")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<BannerIncidenciasDTO> obtenerBannerIncidencias(
            @RequestParam Integer anio,
            @RequestParam Integer mes) {

        BannerIncidenciasDTO banner = incidenciaServicio.obtenerBannerIncidencias(anio, mes);
        return ResponseEntity.ok(banner);
    }
    //ED32
    @GetMapping("/incidencias/grafico-estado-resolucion")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<EstadoResolucionDTO> obtenerEstadoResolucionPorPeriodo(
            @RequestParam Integer anio,
            @RequestParam Integer mes) {

        EstadoResolucionDTO dto = incidenciaServicio.obtenerEstadoResolucionPorPeriodo(anio, mes);
        return ResponseEntity.ok(dto);
    }

    //ED63
    @GetMapping("/incidencias/grafico-tendencia-mensual")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<TendenciaIncidenciasDTO>> obtenerTendenciaMensual(
            @RequestParam Integer anio) {

        List<TendenciaIncidenciasDTO> lista = incidenciaServicio.obtenerTendenciaMensual(anio);
        return ResponseEntity.ok(lista);
    }

    //ED64
    @GetMapping("/incidencias/grafico-distribucion-categoria")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<DistribucionCategoriaDTO>> obtenerDistribucionPorCategoria(
            @RequestParam Integer anio,
            @RequestParam Integer mes) {

        List<DistribucionCategoriaDTO> lista = incidenciaServicio.obtenerDistribucionPorCategoria(anio, mes);
        return ResponseEntity.ok(lista);
    }
}
