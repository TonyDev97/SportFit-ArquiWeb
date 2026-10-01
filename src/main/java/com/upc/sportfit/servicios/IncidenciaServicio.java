package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.IncidenciaDTO;
import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.IncidenciaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public IncidenciaDTO InsertarIncidencia(IncidenciaDTO dto) {

        validarTipo(dto.getTipo());
        validarTexto(dto.getAsunto(), "El asunto", 100);
        validarTexto(dto.getDescripcion(), "La descripción", 300);

        if (dto.getUsuario() == null
                || dto.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el usuario de la incidencia"
            );
        }

        Usuario usuario = usuarioRepositorio
                .findById(dto.getUsuario().getIdUsuario())
                .orElseThrow(() -> new NoSuchElementException(
                        "El usuario no existe"
                ));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new IllegalArgumentException(
                    "El usuario debe estar activo"
            );
        }

        if (usuario.getRol() == null
                || !"CLIENTE".equalsIgnoreCase(
                usuario.getRol().getNombre())) {
            throw new IllegalArgumentException(
                    "La incidencia debe estar asociada a un cliente"
            );
        }

        Incidencia incidencia = new Incidencia();
        incidencia.setTipo(dto.getTipo());
        incidencia.setAsunto(dto.getAsunto().trim());
        incidencia.setDescripcion(dto.getDescripcion().trim());
        incidencia.setUsuario(usuario);
        incidencia.setEstado("Pendiente");
        incidencia.setFCreacion(Instant.now());

        incidencia = incidenciaRepositorio.save(incidencia);

        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    public List<IncidenciaDTO> listarIncidencias() {
        return incidenciaRepositorio.listarOrdenadas()
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    public IncidenciaDTO BuscarIncidenciaPorId(Integer id) {
        return incidenciaRepositorio.findById(id)
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe la incidencia con ID " + id
                ));
    }

    public List<IncidenciaDTO> listarIncidenciasPorTipo(String tipo) {
        validarTipo(tipo);

        return incidenciaRepositorio.findByTipo(tipo)
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    public List<IncidenciaDTO> listarIncidenciasPorEstado(
            String estado) {

        if (!"Pendiente".equals(estado)
                && !"Atendido".equals(estado)) {
            throw new IllegalArgumentException(
                    "El estado debe ser Pendiente o Atendido"
            );
        }

        return incidenciaRepositorio.findByEstado(estado)
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    public List<IncidenciaDTO> listarPorUsuario(Integer idUsuario) {
        validarUsuarioExistente(idUsuario);

        return incidenciaRepositorio.listarPorUsuario(idUsuario)
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    public List<IncidenciaDTO> filtrarPorUsuario(
            Integer idUsuario,
            String tipo,
            LocalDate inicio,
            LocalDate fin) {

        validarUsuarioExistente(idUsuario);
        validarTipo(tipo);
        validarFechas(inicio, fin);

        return incidenciaRepositorio
                .filtrarPorUsuario(idUsuario, tipo, inicio, fin)
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    public List<IncidenciaDTO> filtrarParaAdministrador(
            String tipo,
            LocalDate inicio,
            LocalDate fin) {

        validarTipo(tipo);
        validarFechas(inicio, fin);

        return incidenciaRepositorio
                .filtrarParaAdministrador(tipo, inicio, fin)
                .stream()
                .map(incidencia -> modelMapper.map(
                        incidencia, IncidenciaDTO.class))
                .toList();
    }

    @Transactional
    public IncidenciaDTO editarIncidencia(IncidenciaDTO dto) {

        if (dto.getIdIncidencia() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el ID de la incidencia"
            );
        }

        validarTexto(dto.getRespuestaAdmin(), "La respuesta", 300);

        Incidencia incidencia = incidenciaRepositorio
                .findById(dto.getIdIncidencia())
                .orElseThrow(() -> new NoSuchElementException(
                        "La incidencia no existe"
                ));

        if (!"Pendiente".equals(incidencia.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden responder incidencias pendientes"
            );
        }

        incidencia.setRespuestaAdmin(dto.getRespuestaAdmin().trim());
        incidencia.setFRespuesta(Instant.now());
        incidencia.setEstado("Atendido");

        incidencia = incidenciaRepositorio.save(incidencia);

        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    private void validarUsuarioExistente(Integer idUsuario) {
        if (idUsuario == null
                || !usuarioRepositorio.existsById(idUsuario)) {
            throw new NoSuchElementException(
                    "El usuario no existe"
            );
        }
    }

    private void validarTipo(String tipo) {
        if (!"Técnico".equals(tipo)
                && !"Instalaciones".equals(tipo)
                && !"Preguntas".equals(tipo)) {
            throw new IllegalArgumentException(
                    "El tipo debe ser Técnico, Instalaciones o Preguntas"
            );
        }
    }

    private void validarTexto(
            String texto,
            String campo,
            int maximo) {

        if (texto == null
                || texto.trim().isEmpty()
                || texto.trim().length() > maximo) {
            throw new IllegalArgumentException(
                    campo + " es obligatorio y admite hasta "
                            + maximo + " caracteres"
            );
        }
    }

    private void validarFechas(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            throw new IllegalArgumentException(
                    "El rango de fechas es inválido"
            );
        }
    }
}