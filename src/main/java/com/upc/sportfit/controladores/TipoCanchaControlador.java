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
    private TipoCanchaServicio tipoCanchaServicio;

    @PostMapping("/TipoCancha")
    public TipoCancha insertar(@RequestBody TipoCancha tipoCancha) {
        return tipoCanchaServicio.insertar(tipoCancha);
    }

    @GetMapping("/TipoCanchas")
    public List<TipoCancha> listar() {
        return tipoCanchaServicio.listar();
    }

    @PutMapping("/TipoCancha-Actualizar/{id}")
    public TipoCancha actualizar(@RequestBody TipoCancha tipoCancha) {
        return tipoCanchaServicio.actualizar(tipoCancha);
    }

    @DeleteMapping("/TipoCancha-Eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        tipoCanchaServicio.eliminar(id);
    }

    @GetMapping("/TipoCancha-Id/{id}")
    public TipoCancha buscarPorId(@PathVariable Integer id) {
        return tipoCanchaServicio.buscarPorId(id);
    }

    @GetMapping("/TipoCancha-Deporte/{deporte}")
    public List<TipoCancha> buscarPorDeporte(@PathVariable String deporte) {
        return tipoCanchaServicio.buscarPorDeporte(deporte);
    }
}
