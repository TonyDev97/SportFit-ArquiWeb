package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.TipoCancha;
import com.upc.sportfit.repositorios.TipoCanchaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCanchaServicio {
    @Autowired
    private TipoCanchaRepositorio tipoCanchaRepositorio;

    public TipoCancha insertar(TipoCancha tipoCancha) {
        return tipoCanchaRepositorio.save(tipoCancha);
    }
    public List<TipoCancha> listar(){
        return tipoCanchaRepositorio.findAll();
    }
    public TipoCancha actualizar(TipoCancha tipoCancha) {
        if (tipoCanchaRepositorio.existsById(tipoCancha.getIdTipoCancha())) {
            return tipoCanchaRepositorio.save(tipoCancha);
        }
        return null;
    }
    public void eliminar(Integer id) {
        tipoCanchaRepositorio.deleteById(id);
    }
    public TipoCancha buscarPorId(Integer id) {
        return tipoCanchaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Tipo de cancha no encontrada con ID " + id));
    }
    public List<TipoCancha> buscarPorDeporte(String deporte) {
        return tipoCanchaRepositorio.findByDeporte(deporte);
    }

}
