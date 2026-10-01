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

    @PostMapping("/usuario")
    public Usuario insertar(@RequestBody Usuario usuario){
        return usuarioServicio.insertar(usuario);
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarTodo(){
        return usuarioServicio.listarTodo();
    }

    @GetMapping("/usuarios/activos")
    public List<Usuario> listarActivos() {return usuarioServicio.listarActivos();}


    // Validado
    @PutMapping("/usuario-actualizar")
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioServicio.actualizar(usuarioDTO));
    }

    @PutMapping("/usuario/{id}/estado")
    public Usuario cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado){
        return usuarioServicio.cambiarEstado(id, estado);
    }

    @GetMapping("/usuario/{id}")
    public Usuario buscarPorId(@PathVariable Integer id){
        return usuarioServicio.buscarPorId(id);
    }


    // Buscar clientes por filtros
    @GetMapping("/usuario/clientes")
    public List<Usuario> buscarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) Boolean estado
    ){

        return usuarioServicio.buscarClientes(nombre, dni, estado);
    }


    @PutMapping("/usuario/administrador-password")
    public ResponseEntity<UsuarioDTO> cambiarPassword(
            @PathVariable Integer id,
            @RequestBody String password){

        return ResponseEntity.ok(usuarioServicio.cambiarPassword(id, password));
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


}
