package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.repositorios.CancelacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelacionServicio {

    @Autowired
    private CancelacionRepositorio cancelacionRepositorio;

    // Añadir una cancelación
    public Cancelacion registrar(Cancelacion cancelacion){
        return cancelacionRepositorio.save(cancelacion);
    }

    // Listar todas las cancelaciones
    public List<Cancelacion> listar(){
        return cancelacionRepositorio.findAll();
    }

    // Obtener los datos de la cancion por un id
    public Cancelacion listarPorId(Integer id) {
        return cancelacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Cancelación no encontrada con ID " + id));
    }

    // Eliminación fisica de las cancelaciones
    public void eliminar(Integer id){
        cancelacionRepositorio.deleteById(id);
    }

}
