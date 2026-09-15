package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, Integer> {

    // Busca sedes filtrando por distrito sin importar mayúsculas/minúsculas
    @Query("""
        SELECT s FROM Sede s
        WHERE LOWER(s.distrito) = LOWER(:distrito)
    """)
    List<Sede> encontrarPorDistrito(@Param("distrito") String distrito);
}
