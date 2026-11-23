package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.TipoCanchaDTO;
import com.upc.sportfit.entidades.TipoCancha;
import com.upc.sportfit.repositorios.TipoCanchaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoCanchaServicio {
    @Autowired
    private TipoCanchaRepositorio tipoCanchaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    public TipoCancha insertar(TipoCancha tipoCancha) {
        return tipoCanchaRepositorio.save(tipoCancha);
    }
    public List<TipoCanchaDTO> listar() {
        return tipoCanchaRepositorio.findAll()
                .stream()
                .map(tipoCancha -> modelMapper.map(tipoCancha, TipoCanchaDTO.class))
                .collect(Collectors.toList());
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
