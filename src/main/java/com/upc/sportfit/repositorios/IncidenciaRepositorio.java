package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Integer> {

    @Query("SELECT i FROM Incidencia i ORDER BY i.fCreacion DESC")
    List<Incidencia> ListarResumen();

    @Query("select i from Incidencia i " +
            "where i.usuario.idUsuario = :idUsuario " +
            "order by i.fCreacion desc")
    List<Incidencia> listarPorUsuario(
            @Param("idUsuario") Integer idUsuario
    );

    @Query("SELECT i FROM Incidencia i " +
            "WHERE i.usuario.idUsuario = :idUsuario " +
            "AND (:tipo IS NULL OR LOWER(i.tipo) = LOWER(:tipo)) " +
            "AND (CAST(:fecha AS LocalDate) IS NULL OR CAST(i.fCreacion AS LocalDate) = :fecha) " +
            "ORDER BY i.fCreacion DESC")
    List<Incidencia> filtrarPorUsuarioTipoYFecha(
            @Param("idUsuario") Integer idUsuario,
            @Param("tipo") String tipo,
            @Param("fecha") LocalDate fecha
    );

    @Query("SELECT i FROM Incidencia i WHERE " +
            "(:tipo IS NULL OR i.tipo = :tipo) AND " +
            "(:estado IS NULL OR i.estado = :estado) AND " +
            "(CAST(:fecha AS LocalDate) IS NULL OR CAST(i.fCreacion AS LocalDate) = :fecha) " +
            "ORDER BY i.fCreacion DESC")
    List<Incidencia> filtrarParaAdministrador(
            @Param("tipo") String tipo,
            @Param("estado") String estado,
            @Param("fecha") LocalDate fecha
    );

    // Total de incidencias general
    @Query("SELECT COUNT(i) FROM Incidencia i")
    Long contarTotalHistorico();

    @Query("SELECT COUNT(i) FROM Incidencia i " +
            "WHERE EXTRACT(YEAR FROM COALESCE(i.fRespuesta, i.fCreacion)) = :anio " +
            "AND EXTRACT(MONTH FROM COALESCE(i.fRespuesta, i.fCreacion)) = :mes " +
            "AND LOWER(i.estado) = LOWER(:estado)")
    Long contarPorEstadoAnioYMes(
            @Param("anio") Integer anio,
            @Param("mes") Integer mes,
            @Param("estado") String estado
    );

    // Conteo por estado en un mes y año
    @Query("SELECT COUNT(i) FROM Incidencia i " +
            "WHERE EXTRACT(YEAR FROM i.fCreacion) = :anio " +
            "AND EXTRACT(MONTH FROM i.fCreacion) = :mes " +
            "AND i.estado = :estado")
    Long contarPorEstadoYPeriodo(
            @Param("anio") Integer anio,
            @Param("mes") Integer mes,
            @Param("estado") String estado
    );

    // conteo mensual de incidencias por estado y sede para un año específico
    @Query("SELECT EXTRACT(MONTH FROM i.fCreacion), i.estado, COUNT(i) " +
            "FROM Incidencia i " +
            "WHERE EXTRACT(YEAR FROM i.fCreacion) = :anio " +
            "GROUP BY EXTRACT(MONTH FROM i.fCreacion), i.estado " +
            "ORDER BY EXTRACT(MONTH FROM i.fCreacion) ASC")
    List<Object[]> obtenerTendenciaMensualPorAnio(
            @Param("anio") Integer anio
    );

    // conteo de incidencias agrupado por tipo/categoria, sede y periodo
    @Query("SELECT i.tipo, COUNT(i) " +
            "FROM Incidencia i " +
            "WHERE EXTRACT(YEAR FROM i.fCreacion) = :anio " +
            "AND EXTRACT(MONTH FROM i.fCreacion) = :mes " +
            "GROUP BY i.tipo")
    List<Object[]> obtenerDistribucionPorCategoriaYPeriodo(
            @Param("anio") Integer anio,
            @Param("mes") Integer mes
    );

}