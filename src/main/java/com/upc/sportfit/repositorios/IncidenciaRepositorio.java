package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncidenciaRepositorio
        extends JpaRepository<Incidencia, Integer> {

    List<Incidencia> findByTipo(String tipo);

    List<Incidencia> findByEstado(String estado);

    @Query("select i from Incidencia i order by i.fCreacion desc")
    List<Incidencia> listarOrdenadas();

    @Query("select i from Incidencia i " +
            "where i.usuario.idUsuario = :idUsuario " +
            "order by i.fCreacion desc")
    List<Incidencia> listarPorUsuario(
            @Param("idUsuario") Integer idUsuario
    );

    @Query("select i from Incidencia i " +
            "where i.usuario.idUsuario = :idUsuario " +
            "and i.tipo = :tipo " +
            "and cast(i.fCreacion as date) between :inicio and :fin " +
            "order by i.fCreacion desc")
    List<Incidencia> filtrarPorUsuario(
            @Param("idUsuario") Integer idUsuario,
            @Param("tipo") String tipo,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    @Query("select i from Incidencia i " +
            "where i.tipo = :tipo " +
            "and cast(i.fCreacion as date) between :inicio and :fin " +
            "order by i.fCreacion desc")
    List<Incidencia> filtrarParaAdministrador(
            @Param("tipo") String tipo,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}