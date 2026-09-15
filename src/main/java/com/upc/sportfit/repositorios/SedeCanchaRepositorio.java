package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.SedeCancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeCanchaRepositorio extends JpaRepository<SedeCancha, Integer> {

    // Busca las canchas activas que pertenecen a una sede específica
    @Query("""
        SELECT sc FROM SedeCancha sc 
        WHERE sc.idSede.id = :idSede 
        AND sc.estado = true
    """)
    List<SedeCancha> encontrarActivasPorSede(@Param("idSede") Integer idSede);

    // Busca canchas activas en una sede filtrando por el nombre del deporte (HU01)
    @Query("""
        SELECT sc FROM SedeCancha sc 
        WHERE sc.idSede.id = :idSede 
        AND LOWER(sc.idCancha.deporte) = LOWER(:deporte) 
        AND sc.estado = true
    """)
    List<SedeCancha> encontrarActivasPorSedeYDeporte(
            @Param("idSede") Integer idSede,
            @Param("deporte") String deporte
    );

    // Busca todas las canchas de una sede (activas e inactivas) para administración
    @Query("""
        SELECT sc FROM SedeCancha sc 
        WHERE sc.idSede.id = :idSede
    """)
    List<SedeCancha> encontrarTodasPorSede(@Param("idSede") Integer idSede);
}
