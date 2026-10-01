package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.NotificacionDTO;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.repositorios.NotificacionRepositorio;
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

    public Notificacion registrarNotificacion(Notificacion notificacion) {
        return notificacionRepositorio.save(notificacion);
    }

    public Notificacion listarNotificacionPorId(Integer id) {
        return notificacionRepositorio.findById(id).orElse(new Notificacion());
    }

    public Notificacion marcarComoLeida(Integer id) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setLeido(true);
        return notificacionRepositorio.save(notificacion);
    }

    public void eliminarNotificacion(Integer id) {
        notificacionRepositorio.deleteById(id);
    }
}