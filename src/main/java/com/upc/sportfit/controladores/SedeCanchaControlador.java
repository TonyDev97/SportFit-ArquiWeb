package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.servicios.SedeCanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.upc.sportfit.dtos.SedeCanchaDTO;

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
        sedeCancha.setIdSedeCancha(id);
        return sedeCanchaServicio.actualizar(sedeCancha);
    }

    @DeleteMapping("/cancha/{id}")
    public void eliminar(@PathVariable Integer id) {
        sedeCanchaServicio.eliminar(id);
    }

    // HU14: Consultar espacios deportivos utilizando DTO
    @GetMapping("/canchas/espacios")
    public List<SedeCanchaDTO> listarEspacios() {
        return sedeCanchaServicio.listarEspacios();
    }

    // HU14: Consultar un espacio deportivo utilizando DTO
    @GetMapping("/cancha/espacio/{id}")
    public SedeCanchaDTO buscarEspacio(@PathVariable Integer id) {
        return sedeCanchaServicio.buscarEspacio(id);
    }

    // HU14: Actualizar espacio deportivo utilizando DTO
    @PutMapping("/cancha/espacio/{id}")
    public SedeCanchaDTO actualizarEspacio(@PathVariable Integer id, @RequestBody SedeCanchaDTO dto) {
        return sedeCanchaServicio.actualizarEspacio(id, dto);
    }

    // HU14: Cambiar estado del espacio deportivo
    @PutMapping("/sede-cancha/{idCancha}/estado")
    public SedeCanchaDTO cambiarEstadoEspacio(@PathVariable Integer idCancha, @RequestParam Boolean estado) {
        return sedeCanchaServicio.cambiarEstadoEspacio(idCancha, estado);
    }
}
