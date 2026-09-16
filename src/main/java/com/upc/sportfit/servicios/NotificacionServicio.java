package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.repositorios.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServicio {

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    public List<Notificacion> listarNotificaciones() {
        return notificacionRepositorio.findAll();
    }

    public Notificacion registrarNotificacion(Notificacion notificacion) {
        return notificacionRepositorio.save(notificacion);
    }

    public Notificacion listarNotificacionPorId(Long id) {
        return notificacionRepositorio.findById(id).orElse(new Notificacion());
    }

    public void eliminarNotificacion(Long id) {
        notificacionRepositorio.deleteById(id);
    }
}