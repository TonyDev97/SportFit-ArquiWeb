package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.repositorios.SedeCanchaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeCanchaServicio {

    @Autowired
    private SedeCanchaRepositorio sedeCanchaRepositorio;

    public List<SedeCancha> listarPorSedeActivas(Integer idSede) {
        return sedeCanchaRepositorio.encontrarActivasPorSede(idSede);
    }

    public List<SedeCancha> listarPorSedeYDeporte(Integer idSede, String deporte) {
        return sedeCanchaRepositorio.encontrarActivasPorSedeYDeporte(idSede, deporte);
    }

    public List<SedeCancha> listarTodasPorSede(Integer idSede) {
        return sedeCanchaRepositorio.encontrarTodasPorSede(idSede);
    }

    public List<SedeCancha> listar() {
        return sedeCanchaRepositorio.findAll();
    }

    public SedeCancha buscarPorId(Integer id) {
        return sedeCanchaRepositorio.findById(id).orElse(null);
    }

    public SedeCancha insertar(SedeCancha sedeCancha) {
        return sedeCanchaRepositorio.save(sedeCancha);
    }

    public SedeCancha actualizar(SedeCancha sedeCancha) {
        return sedeCanchaRepositorio.save(sedeCancha);
    }

    public void eliminar(Integer id) {
        sedeCanchaRepositorio.deleteById(id);
    }
}