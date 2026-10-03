package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Sede;
import com.upc.sportfit.servicios.SedeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SedeControlador {

    @Autowired
    private SedeServicio sedeServicio;

    @GetMapping("/sede")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<Sede> listar() {
        return sedeServicio.listar();
    }

    @GetMapping("/sede/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public Sede buscarPorId(@PathVariable Integer id) {
        return sedeServicio.buscarPorId(id);
    }

    @GetMapping("/sedes/distrito/{distrito}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<Sede> listarPorDistrito(@PathVariable String distrito) {
        return sedeServicio.listarPorDistrito(distrito);
    }

    @PostMapping("/sede")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Sede insertar(@RequestBody Sede sede) {
        return sedeServicio.insertar(sede);
    }

    @PutMapping("/sede/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Sede actualizar(@PathVariable Integer id, @RequestBody Sede sede) {
        sede.setIdSede(id);
        return sedeServicio.actualizar(sede);
    }

    @DeleteMapping("/sede/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@PathVariable Integer id) {
        sedeServicio.eliminar(id);
    }
}

