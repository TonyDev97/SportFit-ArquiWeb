package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    //ED 36 - H11 - Obtener las notificaciones del usuario por ID usuario
    @GetMapping("/notificacion/cliente/{idUsuario}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<NotificacionDTO>> listarPorIdUsuario(
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(notificacionServicio.obtenerNotificacionesUsuario(idUsuario));
    }

    //ED37 - HU11 - Obtener detalle de notificación
    @GetMapping("/notificacion/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<NotificacionDTO> listarId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(notificacionServicio.listarNotificacionPorId(id));
    }

    //ED no existente en la tabla mejorar HU? - registrar notificación
    /*
    @PostMapping("/notificacion")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Notificacion registrar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }*/

    //ED39 - HU11 - Marcar notificación como leida
    @PutMapping("/notificacion-leida/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacionServicio.marcarComoLeida(id));
    }

    //ED 40 - HU11 - eliminar notificación
    @DeleteMapping("/notificacion-eliminar/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        notificacionServicio.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    /*
    //ED pruebas swagger postman no usado en HUs
    @GetMapping("/notificaciones")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<NotificacionDTO> listar() {
        return notificacionServicio.listarNotificaciones();
    }

    //ED pruebas swagger postman no usado en HUs
    @PutMapping("notificacion-actualizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Notificacion actualizar(@RequestBody Notificacion notificacion) {
        return notificacionServicio.registrarNotificacion(notificacion);
    }*/
}
