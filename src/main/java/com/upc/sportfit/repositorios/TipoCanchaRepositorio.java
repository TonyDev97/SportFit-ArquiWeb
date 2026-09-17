package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.TipoCancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCanchaRepositorio extends JpaRepository<TipoCancha, Integer> {

    List<TipoCancha> findByDeporte(String deporte);
}
