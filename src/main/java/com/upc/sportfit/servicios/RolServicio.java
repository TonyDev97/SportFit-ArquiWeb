package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio {
    @Autowired
    private RolRepositorio rolRepositorio;

    public Rol insertar(Rol rol){
        return rolRepositorio.save(rol);
    }

    public List<Rol> Listar(){
        return rolRepositorio.findAll();
    }

    public Rol actualizar(Rol rol){
        if(rolRepositorio.existsById(rol.getId()))
            return rolRepositorio.save(rol);
        return null;
    }


}
