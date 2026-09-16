package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @GetMapping
    public List<Notificacion> listar() {
        return notificacionServicio.listarNotificaciones();
    }

    @PostMapping
    public Notificacion registrar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }

    @GetMapping("/{id}")
    public Notificacion listarId(@PathVariable("id") Long id) {
        return notificacionServicio.listarNotificacionPorId(id);
    }

    @PutMapping
    public Notificacion actualizar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        notificacionServicio.eliminarNotificacion(id);
    }
}