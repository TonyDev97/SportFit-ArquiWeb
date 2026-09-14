package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.TipoCancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanchaRepositorio extends JpaRepository<TipoCancha, Integer> {


}
