package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.NotificacionRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class NotificacionServicio {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    //HU11 - ED36 - Obtener las notificaciones del usuario por ID usuario
    public List<NotificacionDTO> obtenerNotificacionesUsuario(Integer idUsuario) {
        //Notificaciones de un usuario revisar repository
        List<Notificacion> notificaciones = notificacionRepositorio.findByUsuario_IdUsuario(idUsuario);
        List<NotificacionDTO> notificacionDTOS = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOS.add(modelMapper.map(notificacion, NotificacionDTO.class));
        }
        return notificacionDTOS;
    }

    //ED Pruebas
    public List<NotificacionDTO> listarNotificaciones() {
        List<Notificacion> notificaciones = notificacionRepositorio.findAll();
        List<NotificacionDTO> notificacionDTOs = new ArrayList<>();
        for (Notificacion notificacion : notificaciones) {
            notificacionDTOs.add(
                    modelMapper.map(notificacion, NotificacionDTO.class)
            );
        }
        return notificacionDTOs;
    }

    //HU11 - ED34 - Marcar como leida
    public Notificacion marcarComoLeida(Integer id) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setLeido(true);
        return notificacionRepositorio.save(notificacion);
    }

    //Registrar notificaciones - PARA ED 38
    public Notificacion registrarNotificacion(Notificacion notificacion) {
        return notificacionRepositorio.save(notificacion);
    }

    //ED37 - Obtener detalle de notificacion por ID
    public Notificacion listarNotificacionPorId(Integer id) {
        return notificacionRepositorio.findById(id).orElse(new Notificacion());
    }

    //ED 40 - Eliminar una notificacion
    public void eliminarNotificacion(Integer id) {
        notificacionRepositorio.deleteById(id);
    }
}