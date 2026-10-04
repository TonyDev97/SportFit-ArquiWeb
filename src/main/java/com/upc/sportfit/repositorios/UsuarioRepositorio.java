package com.upc.sportfit.repositorios;

import com.upc.sportfit.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByActivoTrue();
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByDniContaining(String dni);
    List<Usuario> findByActivo(Boolean estado);

    List<Usuario> findByRol_IdRol(Integer idRol);

    // --> Estas 3 consultas reciben el id del servicio

    // Buscar clientes por nombre si contiene VALIDADO
    List<Usuario> findByNombreContainingIgnoreCaseAndRol_IdRol(String nombre, Integer idRol);

    // Buscar clientes por DNI si empieza VALIDADO
    List<Usuario> findByDniStartingWithAndRol_IdRol(String dni, Integer rolIdRol);

    // Buscar clientes por estado VALIDADO
    List<Usuario> findByActivoAndRol_IdRol(Boolean estado, Integer idRol);

    List<Usuario> findByTelefonoStartingWithAndRol_IdRol(String telefono, Integer rolIdRol);

    List<Usuario> findByCorreoEndingWithAndRol_IdRol(String correo, Integer rolIdRol);

    @Query("select r.usuario from Reserva r where r.usuario.rol.idRol = 1 group by r.usuario having count(r) = :cantidad_reservas")
    List<Usuario> buscarClientesCantidadReservas(@Param("cantidad_reservas")  Integer cantidadResevas);

    Optional<Usuario> findByCorreo(String correo);

}
