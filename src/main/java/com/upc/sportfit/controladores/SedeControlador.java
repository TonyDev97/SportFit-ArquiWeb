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

    @GetMapping("/sede")
    public List<Sede> listar() {
        return sedeServicio.listar();
    }

    @GetMapping("/sede/{id}")
    public Sede buscarPorId(@PathVariable Integer id) {
        return sedeServicio.buscarPorId(id);
    }

    @GetMapping("/sedes/distrito/{distrito}")
    public List<Sede> listarPorDistrito(@PathVariable String distrito) {
        return sedeServicio.listarPorDistrito(distrito);
    }

    @PostMapping("/sede")
    public Sede insertar(@RequestBody Sede sede) {
        return sedeServicio.insertar(sede);
    }

    @PutMapping("/sede/{id}")
    public Sede actualizar(@PathVariable Integer id, @RequestBody Sede sede) {
        sede.setIdSede(id);
        return sedeServicio.actualizar(sede);
    }

    @DeleteMapping("/sede/{id}")
    public void eliminar(@PathVariable Integer id) {
        sedeServicio.eliminar(id);
    }
}

