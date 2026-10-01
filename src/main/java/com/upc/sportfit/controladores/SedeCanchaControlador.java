package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.servicios.SedeCanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.upc.sportfit.dtos.SedeCanchaDTO;

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
    //VALIDADO
    @GetMapping("/cancha/sede/{idSede}/deporte/{deporte}")
    public List<SedeCancha> listarPorSedeYDeporte(@PathVariable Integer idSede, @PathVariable String deporte) {
        return sedeCanchaServicio.listarPorSedeYDeporte(idSede, deporte);
    }

    @GetMapping("/cancha/{id}")
    public SedeCanchaDTO buscarPorId(@PathVariable Integer id) {
        return sedeCanchaServicio.buscarEspacio(id);
    }

    @GetMapping("/canchas")
    public List<SedeCanchaDTO> listar() {
        return sedeCanchaServicio.listarEspacios();
    }

    @PostMapping("/cancha")
    public SedeCancha insertar(@RequestBody SedeCancha sedeCancha) {
        return sedeCanchaServicio.insertar(sedeCancha);
    }

    @PutMapping("/cancha/{id}")
    public SedeCanchaDTO actualizar(
            @PathVariable Integer id,
            @RequestBody SedeCanchaDTO sedeCancha) {

        return sedeCanchaServicio.actualizarEspacio(id, sedeCancha);
    }

    @DeleteMapping("/cancha/{id}")
    public void eliminar(@PathVariable Integer id) {
        sedeCanchaServicio.eliminar(id);
    }

    @PutMapping("/sede-cancha/{idCancha}/estado")
    public SedeCanchaDTO cambiarEstadoEspacio(
            @PathVariable Integer idCancha,
            @RequestParam Boolean estado) {

        return sedeCanchaServicio.cambiarEstadoEspacio(idCancha, estado);
    }
}
