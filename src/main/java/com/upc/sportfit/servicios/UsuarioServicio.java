package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.RolRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolRepositorio rolRepositorio;

    public Usuario insertar(Usuario usuario){
        Rol rol = rolRepositorio.findById(usuario.getRol().getIdRol()).orElse(null);
        usuario.setRol(rol);
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> listarTodo(){
        return usuarioRepositorio.findAll();
    }

    public List<Usuario> listarActivos(){
        return usuarioRepositorio.findByActivoTrue();
    }


    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO){
        return usuarioRepositorio.findById(usuarioDTO.getIdUsuario())
                .map(usuario -> {
                    modelMapper.map(usuario, usuarioDTO);
                    return modelMapper.map(usuarioRepositorio.save(usuario), UsuarioDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + usuarioDTO.getIdUsuario()));
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

    public UsuarioDTO cambiarPassword(Integer id, String password){

        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

        if(usuario == null){
            throw new RuntimeException("No es existe el usuario para cambiar la contraseña");
        }
        return modelMapper.map(usuario, UsuarioDTO.class) ;
    }

    public List<UsuarioDTO> buscarClientesNombreContiene(String nombre){
        List<Usuario> clientes = usuarioRepositorio.findByNombreContainingIgnoreCaseAndRol_IdRol(nombre, 1);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }

    public List<UsuarioDTO> buscarClientesDniInicia(String dni){
        List<Usuario> clientes = usuarioRepositorio.findByDniStartingWithAndRol_IdRol(dni, 1);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }
    public List<UsuarioDTO> buscarClientesEstado(Boolean estado){
        List<Usuario> clientes =  usuarioRepositorio.findByActivoAndRol_IdRol(estado, 1);
        if (clientes == null){
            throw new RuntimeException("No existen usuarios con el estado: " + estado);
        }
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }


}
