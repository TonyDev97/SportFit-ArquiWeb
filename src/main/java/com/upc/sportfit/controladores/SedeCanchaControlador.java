package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.servicios.SedeCanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SedeCanchaControlador {

    @Autowired
    private SedeCanchaServicio sedeCanchaServicio;

    @GetMapping("/cancha/sede/{idSede}")
    public List<SedeCancha> listarCanchasPorSede(@PathVariable Integer idSede) {
        return sedeCanchaServicio.listarPorSedeActivas(idSede);
    }

    @GetMapping("/cancha/sede/{idSede}/deporte/{deporte}")
    public List<SedeCancha> listarPorSedeYDeporte(@PathVariable Integer idSede, @PathVariable String deporte) {
        return sedeCanchaServicio.listarPorSedeYDeporte(idSede, deporte);
    }

    @GetMapping("/cancha/{id}")
    public SedeCancha buscarPorId(@PathVariable Integer id) {
        return sedeCanchaServicio.buscarPorId(id);
    }

    @GetMapping("/canchas")
    public List<SedeCancha> listar() {
        return sedeCanchaServicio.listar();
    }

    @PostMapping("/cancha")
    public SedeCancha insertar(@RequestBody SedeCancha sedeCancha) {
        return sedeCanchaServicio.insertar(sedeCancha);
    }

    @PutMapping("/cancha/{id}")
    public SedeCancha actualizar(@PathVariable Integer id, @RequestBody SedeCancha sedeCancha) {
        sedeCancha.setId(id);
        return sedeCanchaServicio.actualizar(sedeCancha);
    }

    @DeleteMapping("/cancha/{id}")
    public void eliminar(@PathVariable Integer id) {
        sedeCanchaServicio.eliminar(id);
    }
}
