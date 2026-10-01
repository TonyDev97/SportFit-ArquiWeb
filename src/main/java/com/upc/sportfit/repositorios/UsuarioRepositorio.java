package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByActivoTrue();
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByDniContaining(String dni);
    List<Usuario> findByActivo(Boolean estado);

    // --> Estas 3 consultas reciben el id del servicio

    // Buscar clientes por nombre si contiene VALIDADO
    List<Usuario> findByNombreContainingIgnoreCaseAndRol_IdRol(String nombre, Integer idRol);

    // Buscar clientes por DNI si empieza VALIDADO
    List<Usuario> findByDniStartingWithAndRol_IdRol(String dni, Integer rolIdRol);

    // Buscar clientes por estado VALIDADO
    List<Usuario> findByActivoAndRol_IdRol(Boolean estado, Integer idRol);


}
