package com.upc.sporfit.controladores;

import com.upc.sporfit.entidades.TipoCancha;
import com.upc.sporfit.servicios.CanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CanchaControlador {
    @Autowired
    private CanchaServicio canchaServicio;

    @PostMapping("/Cancha")
    public TipoCancha insertar(@RequestBody TipoCancha tipoCancha) {return canchaServicio.insertar(tipoCancha);}

    @GetMapping("/Canchas")
    public List<TipoCancha> listar() { return canchaServicio.listar();}

    @PutMapping("/{id}")
    public TipoCancha actualizar(@PathVariable Long id, @RequestBody TipoCancha tipoCancha) {
        return canchaServicio.actualizar(id, tipoCancha);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        canchaServicio.eliminar(id);
    }

}
