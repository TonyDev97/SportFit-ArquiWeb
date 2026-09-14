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

}
