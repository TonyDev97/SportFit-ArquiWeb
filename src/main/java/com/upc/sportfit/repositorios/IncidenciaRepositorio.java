package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Integer> {
    // Buscar incidencias por tipo
    List<Incidencia> findByTipo(String tipo);

    // Buscar incidencias por estado
    List<Incidencia> findByEstado(String estado);
}
