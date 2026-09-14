package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, Integer> {
    List<Sede> findByDistritoIgnoreCase(String distrito); //facilita la buscqueda
}
