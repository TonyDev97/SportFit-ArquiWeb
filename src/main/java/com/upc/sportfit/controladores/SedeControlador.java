package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Sede;
import com.upc.sportfit.servicios.SedeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SedeControlador {
    @Autowired
    private SedeServicio sedeServicio;

    // Endpoint solicitado en la tabla de requerimientos (/api/sede)
    @GetMapping("/sede")
    public List<Sede> listar(){
        return sedeServicio.Listar();
    }

    // Endpoint opcional pluralizado comúnmente usado en REST
    @GetMapping("/sedes")
    public List<Sede> listarSedes(){
        return sedeServicio.Listar();
    }

    @PostMapping("/sede")
    public Sede insertar(@RequestBody Sede sede){
        return sedeServicio.Insertar(sede);
    }

    @PutMapping("/sede/{id}")
    public Sede actualizar(@PathVariable Integer id, @RequestBody Sede sede){
        sede.setId(id);
        return sedeServicio.actualizar(sede);
    }

    // Endpoint adicional: obtener el detalle de una sola sede
    @GetMapping("/sede/{id}")
    public Sede buscarPorId(@PathVariable Integer id){
        return sedeServicio.buscarPorId(id);
    }

    // Endpoint adicional: filtrar sedes por distrito (ideal para búsqueda de clientes)
    @GetMapping("/sedes/distrito/{distrito}")
    public List<Sede> listarPorDistrito(@PathVariable String distrito){
        return sedeServicio.listarPorDistrito(distrito);
    }
}

