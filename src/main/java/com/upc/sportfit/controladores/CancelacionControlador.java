package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.servicios.CancelacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CancelacionControlador {
    @Autowired
    private CancelacionServicio cancelacionServicio;

    @PostMapping("/cancelacion")
    public Cancelacion registrar(@RequestBody Cancelacion cancelacion) {
        return cancelacionServicio.registrar(cancelacion);
    }

    @GetMapping("/cancelaciones")
    public List<Cancelacion> listar(){
        return cancelacionServicio.listar();
    }

    @GetMapping("/cancelaciones/{id}")
    public Cancelacion listarPorId(@PathVariable Integer id){
        return cancelacionServicio.listarPorId(id);
    }

    @DeleteMapping("/cancelacion/{id}")
    public void eliminar(@PathVariable Integer id){
        cancelacionServicio.eliminar(id);
    }

}
