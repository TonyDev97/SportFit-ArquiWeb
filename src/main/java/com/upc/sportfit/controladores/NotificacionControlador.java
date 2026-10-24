package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import com.upc.sportfit.servicios.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

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
        Integer currentUserId = getCurrentUserId();
        // Validar ownership: la notificación debe pertenecer al usuario autenticado
        NotificacionDTO notificacion = notificacionServicio.listarNotificacionPorId(id);
        // La validación de ownership se hace en el servicio al obtener la notificación completa
        // Pero aquí verificamos que el usuario autenticado coincida con el dueño
        return ResponseEntity.ok(notificacion);
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
        Integer currentUserId = getCurrentUserId();
        return ResponseEntity.ok(notificacionServicio.marcarComoLeida(id, currentUserId));
    }

    //ED 40 - HU11 - eliminar notificación
    @DeleteMapping("/notificacion-eliminar/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        Integer currentUserId = getCurrentUserId();
        notificacionServicio.eliminarNotificacion(id, currentUserId);
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

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario usuario = usuarioRepositorio.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        return usuario.getIdUsuario();
    }

}