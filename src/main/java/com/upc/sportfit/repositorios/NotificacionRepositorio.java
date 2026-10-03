package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Integer> {
    //Query method para obtener las notificaciones de un usuario en especifico
    List<Notificacion> findByUsuario_IdUsuario(Integer idUsuario);
}