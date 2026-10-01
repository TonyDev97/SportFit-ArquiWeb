package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.IncidenciaDTO;
import com.upc.sportfit.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidenciaControlador {

    @Autowired
    private IncidenciaServicio incidenciaServicio;

    @PostMapping("/incidencia")
    public ResponseEntity<IncidenciaDTO> insertar(
            @RequestBody IncidenciaDTO dto) {

        return ResponseEntity.ok(
                incidenciaServicio.InsertarIncidencia(dto)
        );
    }

    @GetMapping("/incidencias")
    public ResponseEntity<List<IncidenciaDTO>> listar() {
        return ResponseEntity.ok(
                incidenciaServicio.listarIncidencias()
        );
    }

    @GetMapping("/incidencia-id/{id}")
    public ResponseEntity<IncidenciaDTO> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                incidenciaServicio.BuscarIncidenciaPorId(id)
        );
    }

    @GetMapping("/incidencias-tipo/{tipo}")
    public ResponseEntity<List<IncidenciaDTO>> listarPorTipo(
            @PathVariable String tipo) {

        return ResponseEntity.ok(
                incidenciaServicio.listarIncidenciasPorTipo(tipo)
        );
    }

    @GetMapping("/incidencias-estado/{estado}")
    public ResponseEntity<List<IncidenciaDTO>> listarPorEstado(
            @PathVariable String estado) {

        return ResponseEntity.ok(
                incidenciaServicio.listarIncidenciasPorEstado(estado)
        );
    }

    @GetMapping("/incidencia/usuario/{idUsuario}")
    public ResponseEntity<List<IncidenciaDTO>> listarPorUsuario(
            @PathVariable Integer idUsuario) {

        return ResponseEntity.ok(
                incidenciaServicio.listarPorUsuario(idUsuario)
        );
    }

    @GetMapping("/incidencia/usuario/{idUsuario}/filtro")
    public ResponseEntity<List<IncidenciaDTO>> filtrarPorUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam String tipo,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return ResponseEntity.ok(
                incidenciaServicio.filtrarPorUsuario(
                        idUsuario, tipo, fechaInicio, fechaFin
                )
        );
    }

    @GetMapping("/incidencia/{tipo}")
    public ResponseEntity<List<IncidenciaDTO>> filtrarAdministrador(
            @PathVariable String tipo,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return ResponseEntity.ok(
                incidenciaServicio.filtrarParaAdministrador(
                        tipo, fechaInicio, fechaFin
                )
        );
    }

    @PutMapping("/incidencia-actualizar")
    public ResponseEntity<IncidenciaDTO> responder(
            @RequestBody IncidenciaDTO dto) {

        return ResponseEntity.ok(
                incidenciaServicio.editarIncidencia(dto)
        );
    }
}