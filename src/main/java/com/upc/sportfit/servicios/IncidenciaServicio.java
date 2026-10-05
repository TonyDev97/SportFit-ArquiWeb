package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.IncidenciaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncidenciaServicio {

    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Incidencia InsertarIncidencia(Incidencia incidencia) {
        return registrarIncidenciaCliente(incidencia);
    }

    public List<Incidencia> listarIncidencias(){

        return incidenciaRepositorio.findAll();
    }
    public Incidencia BuscarIncidenciaPorId(Integer id){
        return incidenciaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Incidencia no encontrada con ID " + id));
    }
    public List<Incidencia> listarIncidenciasPorTipo(String tipo){

        return incidenciaRepositorio.findByTipo(tipo);
    }
    public List<Incidencia> listarIncidenciasPorEstado(String estado){
        return incidenciaRepositorio.findByEstado(estado);
    }
    @Transactional
    public Incidencia editarIncidencia(Incidencia incidencia) {
        return responderIncidenciaPendiente(incidencia);
    }

    public List<Incidencia> listarPorUsuario(Integer idUsuario) {
        validarUsuarioExistente(idUsuario);
        return incidenciaRepositorio.listarPorUsuario(idUsuario);
    }

    public List<Incidencia> filtrarPorUsuario(Integer idUsuario, String tipo, LocalDate inicio, LocalDate fin) {
        validarUsuarioExistente(idUsuario);
        validarTipo(tipo);
        validarFechas(inicio, fin);
        return incidenciaRepositorio.filtrarPorUsuario(idUsuario, tipo, inicio, fin);
    }

    public List<Incidencia> filtrarParaAdministrador(String tipo, LocalDate inicio, LocalDate fin) {
        validarTipo(tipo);
        validarFechas(inicio, fin);
        return incidenciaRepositorio.filtrarParaAdministrador(tipo, inicio, fin);
    }

    // HU12 - ED36: Registrar una incidencia de un cliente activo.
    @Transactional
    public Incidencia registrarIncidenciaCliente(Incidencia datos) {

        if (datos == null
                || datos.getUsuario() == null
                || datos.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("Debe indicar el cliente");
        }

        validarTipo(datos.getTipo());
        validarTexto(datos.getAsunto(), "El asunto", 100);
        validarTexto(datos.getDescripcion(), "La descripción", 300);

        Usuario cliente = usuarioRepositorio
                .findById(datos.getUsuario().getIdUsuario())
                .orElseThrow(() ->
                        new IllegalArgumentException("El cliente no existe"));

        if (!Boolean.TRUE.equals(cliente.getActivo())
                || cliente.getRol() == null
                || !"CLIENTE".equals(cliente.getRol().getNombre())) {
            throw new IllegalArgumentException(
                    "Debe indicar un cliente activo");
        }

        Incidencia nueva = new Incidencia();
        nueva.setTipo(datos.getTipo());
        nueva.setAsunto(datos.getAsunto().trim());
        nueva.setDescripcion(datos.getDescripcion().trim());
        nueva.setUsuario(cliente);
        nueva.setEstado("Pendiente");
        nueva.setFCreacion(Instant.now());

        return incidenciaRepositorio.save(nueva);
    }

    // HU12 - ED41: Responder una incidencia pendiente.
    @Transactional
    public Incidencia responderIncidenciaPendiente(Incidencia datos) {

        if (datos == null || datos.getIdIncidencia() == null) {
            throw new IllegalArgumentException("Debe indicar la incidencia");
        }

        validarTexto(datos.getRespuestaAdmin(), "La respuesta", 300);

        Incidencia existente = incidenciaRepositorio
                .findById(datos.getIdIncidencia())
                .orElseThrow(() ->
                        new IllegalArgumentException("La incidencia no existe"));

        if (!"Pendiente".equals(existente.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden responder incidencias pendientes");
        }

        existente.setRespuestaAdmin(datos.getRespuestaAdmin().trim());
        existente.setFRespuesta(Instant.now());
        existente.setEstado("Atendido");

        return incidenciaRepositorio.save(existente);
    }


    //MÉTODOS PRIVADOS DE VALIDACIÓN

    private void validarUsuarioExistente(Integer idUsuario) {
        if (idUsuario == null || !usuarioRepositorio.existsById(idUsuario)) {
            throw new NoSuchElementException("El usuario no existe");
        }
    }

    private void validarTipo(String tipo) {
        if (!"Técnico".equals(tipo) && !"Instalaciones".equals(tipo) && !"Preguntas".equals(tipo)) {
            throw new IllegalArgumentException("El tipo debe ser Técnico, Instalaciones o Preguntas");
        }
    }

    private void validarTexto(String texto, String campo, int maximo) {
        if (texto == null || texto.trim().isEmpty() || texto.trim().length() > maximo) {
            throw new IllegalArgumentException(campo + " es obligatorio y admite hasta " + maximo + " caracteres");
        }
    }

    private void validarFechas(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            throw new IllegalArgumentException("El rango de fechas es inválido");
        }
    }

}
