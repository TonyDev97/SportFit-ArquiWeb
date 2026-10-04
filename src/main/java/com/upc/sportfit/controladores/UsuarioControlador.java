package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    // ED48
    @GetMapping("/api/usuario-clientes-reservas/{cantidadReservas}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesCantidadReservas(@PathVariable Integer cantidadReservas){
        return ResponseEntity.ok(usuarioServicio.buscarClientesCantidadReservas(cantidadReservas));
    }

    // ED51 - Buscar clientes
    @GetMapping("/usuario/clientes")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Usuario> buscarClientes(){
        return usuarioServicio.listarTodo();
    }

    // ED52
    // Validado
    @GetMapping("/usuario/clientes-nombre/{nombre}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<UsuarioDTO> buscarClientesNombreContiene(@PathVariable String nombre){
        return usuarioServicio.buscarClientesNombreContiene(nombre);
    }

    // ED53
    // Validado
    @GetMapping("/usuario/clientes-dni/{dni}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<UsuarioDTO> buscarClientesDniInicia(@PathVariable String dni){
        return usuarioServicio.buscarClientesDniInicia(dni);
    }

    // ED54
    // Validado
    @GetMapping("/usuario/clientes-estado/{estado}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesEstado(@PathVariable Boolean estado){
        return ResponseEntity.ok(usuarioServicio.buscarClientesEstado(estado));
    }

    // ED55
    // Validado
    @PutMapping("/usuario-actualizar")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioServicio.actualizar(usuarioDTO));
    }


    // ED56
    @PutMapping("/usuario/administrador-password/{idAdmin}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> cambiarPassword(
            @PathVariable Integer idAdmin,
            @RequestBody String password){
        return ResponseEntity.ok(usuarioServicio.cambiarPassword(idAdmin, password));
    }

    // ED57
    @PostMapping("/usuario/cliente")
    @PreAuthorize("permitAll()")
    public UsuarioDTO insertar(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioServicio.insertar(usuarioDTO);
    }

    // ED58
    @GetMapping("/usuarios-clientes-telefono/{telefono}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesTelefonoInicia(@PathVariable String telefono){
        return ResponseEntity.ok(usuarioServicio.buscarClientesTelefonoInicia(telefono));
    }

    // ED59
    @GetMapping("/api/usuarios-clientes-correo/{dominio}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClienteCorreoDominio(@PathVariable String dominio){
        return ResponseEntity.ok(usuarioServicio.buscarClienteCorreoDominio(dominio));
    }

    // ED60
    @PutMapping("/usuario/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Usuario cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado){
        return usuarioServicio.cambiarEstado(id, estado);
    }


    // NO SE VAN A USAR
    //@GetMapping("/usuarios")
    //public List<Usuario> listarTodo(){
    //    return usuarioServicio.listarTodo();
    //}

    //@GetMapping("/usuarios/activos")
    //public List<Usuario> listarActivos() {return usuarioServicio.listarActivos();}
    //@GetMapping("/usuario/{id}")
    //public Usuario buscarPorId(@PathVariable Integer id){
    //    return usuarioServicio.buscarPorId(id);    }
}
