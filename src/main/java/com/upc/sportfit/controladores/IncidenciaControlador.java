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

    //ED36: Registrar incidencia
    @PostMapping("/incidencia")
    public Incidencia insertar(@RequestBody Incidencia incidencia) {
        return incidenciaServicio.InsertarIncidencia(incidencia);
    }

    //ED39: Consultar todas las incidencias.
    @GetMapping("/incidencias")
    public List<Incidencia> listar() {
        return incidenciaServicio.listarIncidencias();
    }

    //ED46: Consultar una incidencia por ID.
    @GetMapping("/incidencia-id/{id}")
    public Incidencia BuscarIncidenciaPorId(@PathVariable Integer id){
        return incidenciaServicio.BuscarIncidenciaPorId(id);
    }

    //ED47: Listar incidencias por tipo.
    @GetMapping("/incidencias-tipo/{tipo}")
    public List<Incidencia> listarPorTipo(@PathVariable String tipo) {
        return incidenciaServicio.listarIncidenciasPorTipo(tipo);
    }

    //ED48: Listar incidencias por estado.
    @GetMapping("/incidencias-estado/{estado}")
    public List<Incidencia> listarPorEstado(@PathVariable String estado) {
        return incidenciaServicio.listarIncidenciasPorEstado(estado);
    }

    //ED41: Responder Incidencia pendiente
    @PutMapping("/incidencia-actualizar")
    public Incidencia actualizar(@RequestBody Incidencia incidencia) {
        return incidenciaServicio.editarIncidencia(incidencia);
    }

    //ED37: Consultar el historial de incidencias de un cliente.
    @GetMapping("/incidencia/usuario/{idUsuario}")
    public List<Incidencia> listarPorUsuario(
            @PathVariable Integer idUsuario) {

        return incidenciaServicio.listarPorUsuario(idUsuario);
    }

    //ED38: Filtrar incidencias del cliente por tipo y rango de fechas
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

    //ED89: Filtrar incidencias del administrador por tipo y fechas.
    @GetMapping("/incidencias/filtro")
    public List<Incidencia> filtrarAdministrador(
            @RequestParam String tipo,
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
