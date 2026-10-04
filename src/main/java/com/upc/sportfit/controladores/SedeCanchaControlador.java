package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.SedeCanchaDTO;
import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.servicios.SedeCanchaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.upc.sportfit.dtos.SedeCanchaDTO;

@RestController
@RequestMapping("/api")
public class SedeCanchaControlador {

    @Autowired
    private SedeCanchaServicio sedeCanchaServicio;

    // ED45
    @GetMapping("/cancha/sede/{idSede}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<SedeCancha> listarCanchasPorSede(@PathVariable Integer idSede) {
        return sedeCanchaServicio.listarPorSedeActivas(idSede);
    }

    // ED46
    @GetMapping("/cancha/sede/{idSede}/deporte/{idTipoCancha}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<SedeCanchaDTO>> BuscarPorDeporteYSede(
            @PathVariable("idSede") Integer idSede,
            @PathVariable("idTipoCancha") Integer idTipoCancha) {

        List<SedeCanchaDTO> resultado = sedeCanchaServicio.listarPorDeporteYSede(idTipoCancha, idSede);
        return ResponseEntity.ok(resultado);
    }

    // ED47
    @GetMapping("/canchas")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<SedeCancha> listar() {
        return sedeCanchaServicio.listar();
    }

    // ED49 - HU14: Cambiar estado del espacio deportivo
    @PutMapping("/sede-cancha/{idCancha}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public SedeCanchaDTO cambiarEstadoEspacio(@PathVariable Integer idCancha, @RequestParam Boolean estado) {
        return sedeCanchaServicio.cambiarEstadoEspacio(idCancha, estado);
    }

    // ED50 - HU14: Actualizar espacio deportivo utilizando DTO
    @PutMapping("/cancha/espacio/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public SedeCanchaDTO actualizarEspacio(@PathVariable Integer id, @RequestBody SedeCanchaDTO dto) {
        return sedeCanchaServicio.actualizarEspacio(id, dto);
    }


    /*
    @GetMapping("/cancha/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public SedeCancha buscarPorId(@PathVariable Integer id) {
        return sedeCanchaServicio.buscarPorId(id);
    }

    @PostMapping("/cancha")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public SedeCancha insertar(@RequestBody SedeCancha sedeCancha) {
        return sedeCanchaServicio.insertar(sedeCancha);
    }

    @PutMapping("/cancha/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public SedeCancha actualizar(@PathVariable Integer id, @RequestBody SedeCancha sedeCancha) {
        sedeCancha.setIdSedeCancha(id);
        return sedeCanchaServicio.actualizar(sedeCancha);
    }

    @DeleteMapping("/cancha/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@PathVariable Integer id) {
        sedeCanchaServicio.eliminar(id);
    }

    // HU14: Consultar espacios deportivos utilizando DTO
    @GetMapping("/canchas/espacios")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public List<SedeCanchaDTO> listarEspacios() {
        return sedeCanchaServicio.listarEspacios();
    }

    // HU14: Consultar un espacio deportivo utilizando DTO
    @GetMapping("/cancha/espacio/{id}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public SedeCanchaDTO buscarEspacio(@PathVariable Integer id) {
        return sedeCanchaServicio.buscarEspacio(id);
    }*/

}
