package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolControlador {
    @Autowired
    private RolServicio rolServicio;
    /*
    @PostMapping("/rol")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Rol insertar(Rol rol){
        return rolServicio.insertar(rol);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Rol> listar(){
        return rolServicio.Listar();
    }

    @PutMapping("/rol{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Rol actualizar(Rol rol){
        return rolServicio.actualizar(rol);
    } */
}
