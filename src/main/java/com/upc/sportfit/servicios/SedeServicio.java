package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.SedeDTO;
import com.upc.sportfit.entidades.Sede;
import com.upc.sportfit.repositorios.SedeRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SedeServicio {

    @Autowired
    private SedeRepositorio sedeRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    //ED 43 LISTAR SEDES
    public List<SedeDTO> listar() {
        return sedeRepositorio.findAll()
                .stream()
                .map(sede -> modelMapper.map(sede, SedeDTO.class))
                .toList();
    }




    //NO SE USAN LOS DE ABAJO (NO LOS HE CAMBIADO)

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
