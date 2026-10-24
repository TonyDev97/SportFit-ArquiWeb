package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.NotificacionRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class NotificacionServicio {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //HU11 - ED36 - Obtener las notificaciones del usuario por ID usuario
    public List<NotificacionDTO> obtenerNotificacionesUsuario(Integer idUsuario) {
        List<Notificacion> notificaciones = notificacionRepositorio.findByUsuarioIdUsuarioOrderByFNotificacionDesc(idUsuario);
        List<NotificacionDTO> notificacionDTOS = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOS.add(modelMapper.map(notificacion, NotificacionDTO.class));
        }
        return notificacionDTOS;
    }

    //ED Pruebas
    /*
    public List<NotificacionDTO> listarNotificaciones() {
        List<Notificacion> notificaciones = notificacionRepositorio.findAll();
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(
                    modelMapper.map(notificacion, NotificacionDTO.class)
            );
        }
        return notificacionDTOs;
    }*/

    //HU11 - ED39 - Marcar como leida con validación de ownership
    public NotificacionDTO marcarComoLeida(Integer id, Integer currentUserId) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        if (!notificacion.getUsuario().getIdUsuario().equals(currentUserId)) {
            throw new RuntimeException("No autorizado para modificar esta notificación");
        }

        notificacion.setLeido(true);
        Notificacion saved = notificacionRepositorio.save(notificacion);
        return modelMapper.map(saved, NotificacionDTO.class);
    }

    //Registrar notificaciones ( EN DESUSO)
    /*
    public Notificacion registrarNotificacion(Notificacion notificacion) {
        return notificacionRepositorio.save(notificacion);
    }*/

    //ED37 - HU11 - Obtener detalle de notificación (retorna DTO)
    public NotificacionDTO listarNotificacionPorId(Integer id) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    //ED40 - HU11 - Eliminar notificación con validación de ownership
    public void eliminarNotificacion(Integer id, Integer currentUserId) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        if (!notificacion.getUsuario().getIdUsuario().equals(currentUserId)) {
            throw new RuntimeException("No autorizado para eliminar esta notificación");
        }

        notificacionRepositorio.deleteById(id);
    }

    // Crear notificación automática (para uso interno de otros servicios)
    public NotificacionDTO crearNotificacion(Integer idUsuario, String tipo, String titulo, String mensaje) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setTipo(tipo);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setFNotificacion(Instant.now());
        notificacion.setLeido(false);

        Notificacion saved = notificacionRepositorio.save(notificacion);
        return modelMapper.map(saved, NotificacionDTO.class);
    }
}