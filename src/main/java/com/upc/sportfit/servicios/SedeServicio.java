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

    public Sede Insertar(Sede sede){
        return sedeRepositorio.save(sede);
    }
    public List<Sede> Listar(){
        return sedeRepositorio.findAll();
    }

    public Sede actualizar(Sede sede){
        return sedeRepositorio.save(sede);
    }

    public Sede buscarPorId(Integer id){
        return sedeRepositorio.findById(id).orElse(null);
    }

    public List<Sede> listarPorDistrito(String distrito){
        return sedeRepositorio.findByDistritoIgnoreCase(distrito);
    }

    public void eliminar(Integer id){
        sedeRepositorio.deleteById(id);
    }
}
