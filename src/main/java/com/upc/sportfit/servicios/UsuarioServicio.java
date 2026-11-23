package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.RolRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//importación para la contraseña
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO insertar(UsuarioDTO usuarioDTO){
        Usuario usuario = modelMapper.map(usuarioDTO,Usuario.class);
        Rol rol = rolRepositorio.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new NoSuchElementException("No existe el rol con ID: " + usuarioDTO.getIdRol()));
        usuario.setRol(rol);
        usuario.setFCreacion(Instant.now());
        usuario.setCreadoPor("API");
        usuarioRepositorio.save(usuario);
        return modelMapper.map(usuario,UsuarioDTO.class);
    }

    public Optional<Usuario> buscarPorCorreo(String correo){
        return usuarioRepositorio.findByCorreo(correo);
    }

    public List<UsuarioDTO> listarTodo() {
        return usuarioRepositorio.findByRol_IdRol(1)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }

    //NO SE USA
    public List<Usuario> listarActivos(){
        return usuarioRepositorio.findByActivoTrue();
    }


    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO, String correoAutenticado, boolean esAdmin) {
        Usuario usuario = usuarioRepositorio.findById(usuarioDTO.getIdUsuario())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if (!esAdmin && !usuario.getCorreo().equals(correoAutenticado)) {
            throw new AccessDeniedException("No puedes actualizar otro usuario");
        }

        Usuario datos = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setDni(datos.getDni());
        usuario.setTelefono(datos.getTelefono());
        usuario.setCorreo(datos.getCorreo());
        if (datos.getImgUsuario() != null && !datos.getImgUsuario().isBlank()) {
            usuario.setImgUsuario(datos.getImgUsuario());
        }
        if (usuarioDTO.getContrasenaHash() != null &&
                !usuarioDTO.getContrasenaHash().isBlank()) {
            usuario.setContrasenaHash(
                    passwordEncoder.encode(usuarioDTO.getContrasenaHash())
            );
        }
        usuario.setFModificacion(Instant.now());
        Usuario guardado = usuarioRepositorio.save(usuario);
        return modelMapper.map(guardado, UsuarioDTO.class);
    }



    public UsuarioDTO cambiarEstado(Integer id, Boolean estado) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el usuario con el ID: " + id));

        usuario.setActivo(estado);
        usuario.setFModificacion(Instant.now());
        Usuario guardado = usuarioRepositorio.save(usuario);

        return modelMapper.map(guardado, UsuarioDTO.class);
    }

    // NO SE USA
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

    // NO SE USA
    public Usuario buscarPorId(Integer id){
        return usuarioRepositorio.findById(id).orElse(null);
    }

    // MODIFICAR ACA
    public UsuarioDTO cambiarPassword(Integer id, String password){
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if(usuario == null){
            throw new RuntimeException("No es existe el usuario para cambiar la contraseña");
        }
        usuario.setContrasenaHash(passwordEncoder.encode(password));
        usuarioRepositorio.save(usuario);
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

    public List<UsuarioDTO> buscarClientesTelefonoInicia(String telefono){
        List<Usuario> clientes =  usuarioRepositorio.findByTelefonoStartingWithAndRol_IdRol(telefono,1 );
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }


    public List<UsuarioDTO> buscarClienteCorreoDominio(String dominio){
        List<Usuario> clientes =  usuarioRepositorio.findByCorreoEndingWithAndRol_IdRol(dominio,1);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }

    public List<UsuarioDTO> buscarClientesCantidadReservas(Integer cantidadReservas){
        List<Usuario> clientes =  usuarioRepositorio.buscarClientesCantidadReservas(cantidadReservas);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario cliente : clientes){
            usuarioDTOs.add(modelMapper.map(cliente, UsuarioDTO.class));
        }
        return usuarioDTOs;
    }

    // Agregado por mi
    // obtenerAdministradorClienteCorreo
    public UsuarioDTO obtenerAdministradorClienteCorreo(String correo, Integer idRol){
        Usuario usuario = usuarioRepositorio.findByCorreoAndRolIdRol(correo, idRol);
        UsuarioDTO resultado = modelMapper.map(usuario, UsuarioDTO.class);
        if (resultado == null){
            throw new RuntimeException("No existe el usuario");
        }
        return resultado;
    }


}
