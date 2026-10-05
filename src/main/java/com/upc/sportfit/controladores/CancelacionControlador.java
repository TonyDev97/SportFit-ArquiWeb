package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.CancelacionDTO;
import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.servicios.CancelacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CancelacionControlador {
    @Autowired
    private CancelacionServicio cancelacionServicio;

    //VALIDADO ED26
    @PostMapping("/cancelacion")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<CancelacionDTO> registrar(@RequestBody CancelacionDTO cancelacionDTO) {
        CancelacionDTO cancelacionCreada = cancelacionServicio.registrar(cancelacionDTO);
        return ResponseEntity.ok(cancelacionCreada);
    }

    /*
    @GetMapping("/cancelaciones")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<CancelacionDTO>> listar() {
        List<CancelacionDTO> lista = cancelacionServicio.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/cancelaciones/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<CancelacionDTO> listarPorId(@PathVariable Integer id) {
        CancelacionDTO dto = cancelacionServicio.listarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancelacion/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cancelacionServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
*/
}
