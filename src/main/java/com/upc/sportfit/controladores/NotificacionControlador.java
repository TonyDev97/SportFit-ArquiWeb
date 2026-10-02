package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    //ED 32 - H11 - Obtener las notificaciones del usuario por ID usuario
    @GetMapping("/notificacion/cliente/{idUsuario}")
    public ResponseEntity<List<NotificacionDTO>> listarPorIdUsuario(
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(notificacionServicio.obtenerNotificacionesUsuario(idUsuario));
    }

    //ED33 - HU11 - eliminar notificacion
    @GetMapping("/notificacion/{id}")
    public Notificacion listarId(@PathVariable("id") Integer id) {
        return notificacionServicio.listarNotificacionPorId(id);
    }

    //ED34 - HU11 - Marcar notificación como leida
    @PutMapping("/notificacion-leida/{id}")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacionServicio.marcarComoLeida(id));
    }

    //ED pruebas swagger postman no usado en HUs
    @GetMapping("/notificaciones")
    public List<NotificacionDTO> listar() {
        return notificacionServicio.listarNotificaciones();
    }

    //ED50 HU? - registrar notificación
    @PostMapping("/notificacion")
    public Notificacion registrar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }

    //ED pruebas swagger postman no usado en HUs
    @PutMapping("notificacion-actualizar")
    public Notificacion actualizar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }

    //ED 35 - HU11 - eliminar notificación
    @DeleteMapping("/notificacion-eliminar/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        notificacionServicio.eliminarNotificacion(id);
    }
}