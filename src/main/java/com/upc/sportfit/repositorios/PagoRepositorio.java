package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {

    List<Pago> findByReserva_IdReserva(Integer idReserva);
}