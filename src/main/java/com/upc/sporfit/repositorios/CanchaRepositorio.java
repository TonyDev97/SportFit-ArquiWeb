package com.upc.sporfit.repositorios;

import com.upc.sporfit.entidades.TipoCancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanchaRepositorio extends JpaRepository<TipoCancha, Long> {


}
