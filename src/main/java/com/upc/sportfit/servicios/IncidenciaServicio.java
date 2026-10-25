package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.IncidenciaDTO;
import com.upc.sportfit.dtos.IncidenciaRegistroDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.BannerIncidenciasDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.DistribucionCategoriaDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.EstadoResolucionDTO;
import com.upc.sportfit.dtos.IncidenciaReportes.TendenciaIncidenciasDTO;
import com.upc.sportfit.dtos.IncidenciaRespuestaDTO;
import com.upc.sportfit.dtos.UsuarioDTO;
import com.upc.sportfit.entidades.Incidencia;
import com.upc.sportfit.entidades.Notificacion;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.IncidenciaRepositorio;
import com.upc.sportfit.repositorios.NotificacionRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncidenciaServicio {

    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;


    // ED34
    @Transactional
    public IncidenciaDTO registrarIncidencia(IncidenciaRegistroDTO registroDTO) {
        List<String> tiposPermitidos = List.of("Tecnico", "Instalaciones", "Preguntas");
        if (!tiposPermitidos.contains(registroDTO.getTipo())) {
            throw new RuntimeException("El tipo de incidencia no es válido. Debe ser: Tecnico, Instalaciones o Preguntas");
        }

        Usuario usuario = usuarioRepositorio.findById(registroDTO.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + registroDTO.getUsuario().getIdUsuario()));

        Incidencia incidencia = new Incidencia();
        incidencia.setTipo(registroDTO.getTipo());
        incidencia.setAsunto(registroDTO.getAsunto());
        incidencia.setDescripcion(registroDTO.getDescripcion());
        incidencia.setUsuario(usuario);
        incidencia.setEstado("Pendiente");
        incidencia.setFCreacion(Instant.now());

        incidencia = incidenciaRepositorio.save(incidencia);
        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }
    // ED35
    @Transactional
    public IncidenciaDTO responderIncidencia(IncidenciaRespuestaDTO respuestaDTO) {
        Incidencia incidencia = incidenciaRepositorio.findById(respuestaDTO.getIdIncidencia())
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada con ID: " + respuestaDTO.getIdIncidencia()));

        if (!"Pendiente".equalsIgnoreCase(incidencia.getEstado())) {
            throw new RuntimeException("Solo se pueden responder incidencias en estado Pendiente");
        }

        incidencia.setRespuestaAdmin(respuestaDTO.getRespuestaAdmin());
        incidencia.setFRespuesta(Instant.now());
        incidencia.setEstado("Atendido");
        incidencia = incidenciaRepositorio.save(incidencia);

        Notificacion notificacion = new Notificacion();
        notificacion.setTipo("Incidencia");
        notificacion.setTitulo("Respuesta a tu incidencia");
        notificacion.setMensaje("Tu incidencia '" + incidencia.getAsunto() + "' ha sido atendida.");
        notificacion.setFNotificacion(Instant.now());
        notificacion.setLeido(false);
        notificacion.setUsuario(incidencia.getUsuario());

        notificacionRepositorio.save(notificacion);
        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    //ED29
    @Transactional
    public List<IncidenciaDTO> listarIncidencias() {
        List<Incidencia> listaIncidencias = incidenciaRepositorio.ListarResumen();

        return listaIncidencias.stream()
                .map(incidencia -> {
                    IncidenciaDTO dto = new IncidenciaDTO();

                    dto.setIdIncidencia(incidencia.getIdIncidencia());
                    dto.setTipo(incidencia.getTipo());
                    dto.setAsunto(incidencia.getAsunto());
                    dto.setDescripcion(incidencia.getDescripcion());
                    dto.setEstado(incidencia.getEstado());
                    dto.setFCreacion(incidencia.getFCreacion());

                    if (incidencia.getUsuario() != null) {
                        UsuarioDTO usuarioDTO = new UsuarioDTO();
                        usuarioDTO.setIdUsuario(incidencia.getUsuario().getIdUsuario());
                        usuarioDTO.setNombre(incidencia.getUsuario().getNombre());
                        dto.setUsuario(usuarioDTO);
                    }

                    return dto;
                })
                .toList();
    }

    //ED30
    @Transactional
    public IncidenciaDTO buscarIncidenciaPorId(Integer id) {
        Incidencia incidencia = incidenciaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Incidencia no encontrada con ID: " + id));

        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    // ED27
    public List<IncidenciaDTO> listarPorUsuario(Integer idUsuario) {
        validarUsuarioExistente(idUsuario);
        List<Incidencia> listaIncidencias = incidenciaRepositorio.listarPorUsuario(idUsuario);
        return listaIncidencias.stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .toList();
    }

    //ED28
    @Transactional(readOnly = true)
    public List<IncidenciaDTO> filtrarPorUsuarioTipoYFecha(Integer idUsuario, String tipo, LocalDate fecha) {
        validarUsuarioExistente(idUsuario);

        boolean tieneTipo = (tipo != null && !tipo.isBlank());
        boolean tieneFecha = (fecha != null);

        if (!tieneTipo && !tieneFecha) {
            throw new RuntimeException("Debe seleccionar al menos un criterio de búsqueda (tipo o fecha) para aplicar el filtro.");
        }

        String tipoFiltro = tieneTipo ? tipo : null;
        List<Incidencia> incidenciasFiltradas = incidenciaRepositorio
                .filtrarPorUsuarioTipoYFecha(idUsuario, tipoFiltro, fecha);

        return incidenciasFiltradas.stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .toList();
    }

    // ED33
    @Transactional
    public List<IncidenciaDTO> filtrarParaAdministrador(String tipo, String estado, LocalDate fecha) {
        String tipoFiltro = (tipo != null && !tipo.isBlank()) ? tipo : null;
        String estadoFiltro = (estado != null && !estado.isBlank()) ? estado : null;

        if (tipoFiltro == null && estadoFiltro == null && fecha == null) {
            throw new RuntimeException("Debe seleccionar al menos un criterio de búsqueda (tipo, estado o fecha) para aplicar el filtro.");
        }

        if (tipoFiltro != null) {
            List<String> tiposPermitidos = List.of("Tecnico", "Instalaciones", "Preguntas");
            if (!tiposPermitidos.contains(tipoFiltro)) {
                throw new RuntimeException("El tipo de incidencia no es válido. Debe ser: Tecnico, Instalaciones o Preguntas");
            }
        }

        if (estadoFiltro != null) {
            List<String> estadosPermitidos = List.of("Pendiente", "Atendido");
            if (!estadosPermitidos.contains(estadoFiltro)) {
                throw new RuntimeException("El estado no es válido. Debe ser: Pendiente o Atendido");
            }
        }

        List<Incidencia> listaIncidencias = incidenciaRepositorio.filtrarParaAdministrador(tipoFiltro, estadoFiltro, fecha);
        return listaIncidencias.stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .toList();
    }

    // REPORTE DE INCIDENCIAS

    //ED31
    @Transactional
    public BannerIncidenciasDTO obtenerBannerIncidencias(Integer anio, Integer mes) {
        if (anio == null || mes == null) {
            throw new RuntimeException("Debe seleccionar obligatoriamente año y mes para consultar el reporte.");
        }

        Long totalHistorico = incidenciaRepositorio.contarTotalHistorico();

        Long pendientes = incidenciaRepositorio.contarPorEstadoAnioYMes(anio, mes, "Pendiente");
        Long atendidas = incidenciaRepositorio.contarPorEstadoAnioYMes(anio, mes, "Atendido");

        return new BannerIncidenciasDTO(totalHistorico, pendientes, atendidas);
    }

    //ED32
    @Transactional
    public EstadoResolucionDTO obtenerEstadoResolucionPorPeriodo(Integer anio, Integer mes) {
        if (anio == null || mes == null) {
            throw new RuntimeException("Debe ingresar un año y un mes válidos para consultar el estado de resolución.");
        }

        Long atendidas = incidenciaRepositorio.contarPorEstadoYPeriodo(anio, mes, "Atendido");
        Long pendientes = incidenciaRepositorio.contarPorEstadoYPeriodo(anio, mes, "Pendiente");
        Long totalPeriodo = atendidas + pendientes;

        Double pctAtendidas = totalPeriodo > 0 ? (double) Math.round((atendidas * 100.0 / totalPeriodo) * 10) / 10 : 0.0;
        Double pctPendientes = totalPeriodo > 0 ? (double) Math.round((pendientes * 100.0 / totalPeriodo) * 10) / 10 : 0.0;

        return new EstadoResolucionDTO(atendidas, pendientes, pctAtendidas, pctPendientes);
    }

    //ED63
    @Transactional
    public List<TendenciaIncidenciasDTO> obtenerTendenciaMensual(Integer anio) {
        if (anio == null) {
            throw new RuntimeException("Debe seleccionar obligatoriamente un año para consultar la tendencia.");
        }

        List<Object[]> resultados = incidenciaRepositorio.obtenerTendenciaMensualPorAnio(anio);

        String[] nombresMeses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Dic"};
        List<TendenciaIncidenciasDTO> tendencia = new ArrayList<>();

        for (int mesNum = 1; mesNum <= 12; mesNum++) {
            final int mesActual = mesNum;

            Long atendidas = resultados.stream()
                    .filter(row -> ((Number) row[0]).intValue() == mesActual && "Atendido".equalsIgnoreCase((String) row[1]))
                    .map(row -> (Long) row[2])
                    .findFirst()
                    .orElse(0L);

            Long pendientes = resultados.stream()
                    .filter(row -> ((Number) row[0]).intValue() == mesActual && "Pendiente".equalsIgnoreCase((String) row[1]))
                    .map(row -> (Long) row[2])
                    .findFirst()
                    .orElse(0L);

            tendencia.add(new TendenciaIncidenciasDTO(nombresMeses[mesNum - 1], atendidas, pendientes));
        }
        return tendencia;
    }

    //ED64
    @Transactional
    public List<DistribucionCategoriaDTO> obtenerDistribucionPorCategoria(Integer anio, Integer mes) {
        if (anio == null || mes == null) {
            throw new RuntimeException("Debe ingresar obligatoriamente año y mes para consultar la distribución por categoría.");
        }

        List<Object[]> resultados = incidenciaRepositorio.obtenerDistribucionPorCategoriaYPeriodo(anio, mes);

        long totalPeriodo = resultados.stream()
                .mapToLong(row -> (Long) row[1])
                .sum();

        List<String> categoriasBase = List.of("Tecnico", "Instalaciones", "Preguntas");
        List<DistribucionCategoriaDTO> distribucion = new ArrayList<>();

        for (String cat : categoriasBase) {
            Long cantidad = resultados.stream()
                    .filter(row -> cat.equalsIgnoreCase((String) row[0]))
                    .map(row -> (Long) row[1])
                    .findFirst()
                    .orElse(0L);

            Double porcentaje = totalPeriodo > 0
                    ? (double) Math.round((cantidad * 100.0 / totalPeriodo) * 10) / 10
                    : 0.0;

            distribucion.add(new DistribucionCategoriaDTO(cat, cantidad, porcentaje));
        }
        return distribucion;
    }

    //MÉTODOS PRIVADOS DE VALIDACIÓN

    private void validarUsuarioExistente(Integer idUsuario) {
        if (idUsuario == null || !usuarioRepositorio.existsById(idUsuario)) {
            throw new NoSuchElementException("El usuario no existe");
        }
    }

}
