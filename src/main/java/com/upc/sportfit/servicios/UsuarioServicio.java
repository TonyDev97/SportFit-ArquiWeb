package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.RolRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    public Usuario insertar(Usuario usuario){
        Rol rol = rolRepositorio.findById(usuario.getIdRol().getId()).orElse(null);
        usuario.setIdRol(rol);
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> listarTodo(){
        return usuarioRepositorio.findAll();
    }

    public List<Usuario> listarActivos(){
        return usuarioRepositorio.findByActivoTrue();
    }

    public Usuario actualizar(Usuario usuario){
        if(usuarioRepositorio.existsById(usuario.getId()))
            return usuarioRepositorio.save(usuario);
        return null;
    }

    public Usuario cambiarEstado(Integer id, Boolean estado){
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

        if(usuario != null){
            usuario.setActivo(estado);
            return usuarioRepositorio.save(usuario);
        }
        return null;
    }

    public List<Usuario> buscarClientes(String nombre, String dni, Boolean estado){
        if(nombre != null){
            return usuarioRepositorio.findByNombreContainingIgnoreCase(nombre);
        }

        if(dni != null){
            return usuarioRepositorio.findByDniContaining(dni);
        }

        if(estado != null){
            return usuarioRepositorio.findByActivo(estado);
        }
        return usuarioRepositorio.findAll();
    }

    public Usuario buscarPorId(Integer id){
        return usuarioRepositorio.findById(id).orElse(null);
    }

    public Usuario cambiarPassword(Integer id, String password){

        Usuario usuario = usuarioRepositorio
                .findById(id)
                .orElse(null);

        if(usuario != null){

            usuario.setContrasenaHash(password);

            return usuarioRepositorio.save(usuario);
        }

        return null;
    }
}
