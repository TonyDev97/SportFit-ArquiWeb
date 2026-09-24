package com.upc.sportfit.controladores;

import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/usuario/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        usuario.setIdUsuario(id);
        return usuarioServicio.actualizar(usuario);
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

    @PutMapping("/usuario/{id}/password")
    public Usuario cambiarPassword(
            @PathVariable Integer id,
            @RequestBody String password){

        return usuarioServicio.cambiarPassword(id, password);
    }
}
