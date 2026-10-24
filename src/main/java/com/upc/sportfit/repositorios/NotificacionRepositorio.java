package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Integer> {
    @Query("SELECT n FROM Notificacion n WHERE n.usuario.idUsuario = :idUsuario ORDER BY n.fNotificacion DESC")
    List<Notificacion> findByUsuarioIdUsuarioOrderByFNotificacionDesc(@Param("idUsuario") Integer idUsuario);
}