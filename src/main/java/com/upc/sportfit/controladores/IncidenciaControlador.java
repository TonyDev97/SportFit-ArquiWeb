package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/incidencia")
    public Incidencia insertar(@RequestBody Incidencia incidencia) {
        return incidenciaServicio.InsertarIncidencia(incidencia);
    }
    @GetMapping("/incidencias")
    public List<Incidencia> listar() {
        return incidenciaServicio.listarIncidencias();
    }
    @GetMapping("/incidencia-id/{id}")
    public Incidencia BuscarIncidenciaPorId(@PathVariable Integer id){
        return incidenciaServicio.BuscarIncidenciaPorId(id);
    }

    @GetMapping("/incidencias-tipo/{tipo}")
    public List<Incidencia> listarPorTipo(@PathVariable String tipo) {
        return incidenciaServicio.listarIncidenciasPorTipo(tipo);
    }
    @GetMapping("/incidencias-estado/{estado}")
    public List<Incidencia> listarPorEstado(@PathVariable String estado) {
        return incidenciaServicio.listarIncidenciasPorEstado(estado);
    }
    @PutMapping("/incidencia-actualizar")
    public Incidencia actualizar(@RequestBody Incidencia incidencia) {
        return incidenciaServicio.editarIncidencia(incidencia);
    }

    @GetMapping("/incidencia/usuario/{idUsuario}")
    public List<Incidencia> listarPorUsuario(
            @PathVariable Integer idUsuario) {

        return incidenciaServicio.listarPorUsuario(idUsuario);
    }

    @GetMapping("/incidencia/usuario/{idUsuario}/filtro")
    public List<Incidencia> filtrarPorUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam String tipo,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return incidenciaServicio.filtrarPorUsuario(
                idUsuario, tipo, fechaInicio, fechaFin
        );
    }

    @GetMapping("/incidencia/{tipo}")
    public List<Incidencia> filtrarAdministrador(
            @PathVariable String tipo,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return incidenciaServicio.filtrarParaAdministrador(
                tipo, fechaInicio, fechaFin
        );
    }
}
