package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/usuario/cliente")
    public UsuarioDTO insertar(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioServicio.insertar(usuarioDTO);
    }

    // Validado
    @PutMapping("/usuario-actualizar")
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioServicio.actualizar(usuarioDTO));
    }

    @PutMapping("/usuario/{id}/estado")
    public Usuario cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado){
        return usuarioServicio.cambiarEstado(id, estado);
    }

    @PutMapping("/usuario/administrador-password/{idAdmin}")
    public ResponseEntity<UsuarioDTO> cambiarPassword(
            @PathVariable Integer idAdmin,
            @RequestBody String password){
        return ResponseEntity.ok(usuarioServicio.cambiarPassword(idAdmin, password));
    }


    // Buscar clientes
    @GetMapping("/usuario/clientes")
    public List<Usuario> buscarClientes(){
        return usuarioServicio.listarTodo();
    }

    // Validado
    @GetMapping("/usuario/clientes-nombre/{nombre}")
    public List<UsuarioDTO> buscarClientesNombreContiene(@PathVariable String nombre){
        return usuarioServicio.buscarClientesNombreContiene(nombre);
    }

    // Validado
    @GetMapping("/usuario/clientes-dni/{dni}")
    public List<UsuarioDTO> buscarClientesDniInicia(@PathVariable String dni){
        return usuarioServicio.buscarClientesDniInicia(dni);
    }

    // Validado
    @GetMapping("/usuario/clientes-estado/{estado}")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesEstado(@PathVariable Boolean estado){
        return ResponseEntity.ok(usuarioServicio.buscarClientesEstado(estado));
    }

    @GetMapping("/usuarios-clientes-telefono/{telefono}")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesTelefonoInicia(@PathVariable String telefono){
        return ResponseEntity.ok(usuarioServicio.buscarClientesTelefonoInicia(telefono));
    }

    @GetMapping("/api/usuarios-clientes-correo/{dominio}")
    public ResponseEntity<List<UsuarioDTO>> buscarClienteCorreoDominio(@PathVariable String dominio){
        return ResponseEntity.ok(usuarioServicio.buscarClienteCorreoDominio(dominio));
    }

    @GetMapping("/api/usuario-clientes-reservas/{cantidadReservas}")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesCantidadReservas(@PathVariable Integer cantidadReservas){
        return ResponseEntity.ok(usuarioServicio.buscarClientesCantidadReservas(cantidadReservas));
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
