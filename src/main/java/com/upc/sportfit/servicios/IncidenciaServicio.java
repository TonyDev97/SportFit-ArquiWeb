package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.repositorios.IncidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaServicio {
    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;

    public Incidencia InsertarIncidencia(Incidencia incidencia){

        return incidenciaRepositorio.save(incidencia);
    }
    public List<Incidencia> listarIncidencias(){

        return incidenciaRepositorio.findAll();
    }
    public Incidencia BuscarIncidenciaPorId(Integer id){
        return incidenciaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Incidencia no encontrada con ID " + id));
    }
    public List<Incidencia> listarIncidenciasPorTipo(String tipo){

        return incidenciaRepositorio.findByTipo(tipo);
    }
    public List<Incidencia> listarIncidenciasPorEstado(String estado){
        return incidenciaRepositorio.findByEstado(estado);
    }
    public Incidencia editarIncidencia(Incidencia incidencia) {
        if (incidenciaRepositorio.existsById(incidencia.getIdIncidencia())) {
            return incidenciaRepositorio.save(incidencia);
        }
        return null;
    }
}
