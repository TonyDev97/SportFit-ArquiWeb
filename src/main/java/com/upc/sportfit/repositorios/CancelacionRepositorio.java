package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Cancelacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelacionRepositorio extends JpaRepository<Cancelacion, Integer> {


}
