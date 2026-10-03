package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.servicios.CancelacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CancelacionControlador {
    @Autowired
    private CancelacionServicio cancelacionServicio;
    //VALIDADO
    @PostMapping("/cancelacion")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public Cancelacion registrar(@RequestBody Cancelacion cancelacion) {
        return cancelacionServicio.registrar(cancelacion);
    }

    @GetMapping("/cancelaciones")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Cancelacion> listar(){
        return cancelacionServicio.listar();
    }

    @GetMapping("/cancelaciones/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Cancelacion listarPorId(@PathVariable Integer id){
        return cancelacionServicio.listarPorId(id);
    }

    @DeleteMapping("/cancelacion/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@PathVariable Integer id){
        cancelacionServicio.eliminar(id);
    }

}
