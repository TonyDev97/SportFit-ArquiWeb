package com.upc.sportfit.controladores;

import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"${ip.frontend}"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    // ED48
    @GetMapping("/usuario-clientes-reservas/{cantidadReservas}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesCantidadReservas(@PathVariable Integer cantidadReservas){
        return ResponseEntity.ok(usuarioServicio.buscarClientesCantidadReservas(cantidadReservas));
    }

    // ED51 - Buscar clientes
    @GetMapping("/usuario/clientes")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientes() {
        return ResponseEntity.ok(usuarioServicio.listarTodo());
    }

    // ED52
    // Validado
    @GetMapping("/usuario/clientes-nombre/{nombre}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesNombreContiene(@PathVariable String nombre){
        return ResponseEntity.ok(usuarioServicio.buscarClientesNombreContiene(nombre));
    }

    // ED53
    // Validado
    @GetMapping("/usuario/clientes-dni/{dni}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesDniInicia(@PathVariable String dni){
        return ResponseEntity.ok(usuarioServicio.buscarClientesDniInicia(dni));
    }

    // ED54
    // Validado testeado
    @GetMapping("/usuario/clientes-estado/{estado}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesEstado(@PathVariable Boolean estado){
        return ResponseEntity.ok(usuarioServicio.buscarClientesEstado(estado));
    }

    // ED55
    // Validado funcional para ambos roles tanto admin y cliente
    @PutMapping("/usuario-actualizar")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMINISTRADOR".equals(a.getAuthority()));
        return ResponseEntity.ok(usuarioServicio.actualizar(usuarioDTO, correo, esAdmin));
    }



    // ED56 Solo para el perfil del admin
    @PutMapping("/usuario/administrador-password/{idAdmin}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> cambiarPassword(
            @PathVariable Integer idAdmin,
            @RequestParam String password){
        return ResponseEntity.ok(usuarioServicio.cambiarPassword(idAdmin, password));
    }
    /*
    // ED57
    @PostMapping("/usuario/cliente")
    @PreAuthorize("permitAll()")
    public UsuarioDTO insertar(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioServicio.insertar(usuarioDTO);
    } */

    // ED58
    @GetMapping("/usuarios-clientes-telefono/{telefono}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClientesTelefonoInicia(@PathVariable String telefono){
        return ResponseEntity.ok(usuarioServicio.buscarClientesTelefonoInicia(telefono));
    }

    // ED59
    @GetMapping("/usuarios-clientes-correo/{dominio}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> buscarClienteCorreoDominio(@PathVariable String dominio){
        return ResponseEntity.ok(usuarioServicio.buscarClienteCorreoDominio(dominio));
    }

    // ED60 ok
    @PutMapping("/usuario/{id}/estado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado){
        return ResponseEntity.ok(usuarioServicio.cambiarEstado(id, estado));
    }

    //Esta API permite obtener los datos del cliente/admin al iniciar sesion
    @GetMapping("/obtener-usuario")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> obtenerAdministradorClienteCorreo(@RequestParam String correo, Integer idRol){
        return ResponseEntity.ok(usuarioServicio.obtenerAdministradorClienteCorreo(correo, idRol));
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
