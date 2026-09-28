package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
