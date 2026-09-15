package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.TipoCancha;
import com.upc.sportfit.servicios.TipoCanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TipoCanchaControlador {
    @Autowired
    private TipoCanchaServicio canchaServicio;

    @PostMapping("/Cancha")
    public TipoCancha insertar(@RequestBody TipoCancha tipoCancha) {return canchaServicio.insertar(tipoCancha);}

    @GetMapping("/Canchas")
    public List<TipoCancha> listar() { return canchaServicio.listar();}

    @PutMapping("/{id}")
    public TipoCancha actualizar(@PathVariable Integer id, @RequestBody TipoCancha tipoCancha) {
        return canchaServicio.actualizar(id, tipoCancha);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        canchaServicio.eliminar(id);
    }

}
