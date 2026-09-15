package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Sede;
import com.upc.sportfit.repositorios.SedeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeServicio {

    @Autowired
    private SedeRepositorio sedeRepositorio;

    public List<Sede> listar() {
        return sedeRepositorio.findAll();
    }

    public Sede buscarPorId(Integer id) {
        return sedeRepositorio.findById(id).orElse(null);
    }

    // Nombre de método actualizado para coincidir con el repositorio
    public List<Sede> listarPorDistrito(String distrito) {
        return sedeRepositorio.encontrarPorDistrito(distrito);
    }

    public Sede insertar(Sede sede) {
        return sedeRepositorio.save(sede);
    }

    public Sede actualizar(Sede sede) {
        return sedeRepositorio.save(sede);
    }

    public void eliminar(Integer id) {
        sedeRepositorio.deleteById(id);
    }
}
