package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.CancelacionDTO;
import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.entidades.Reserva;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.CancelacionRepositorio;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import com.upc.sportfit.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;

@Service
public class CancelacionServicio {

    @Autowired
    private CancelacionRepositorio cancelacionRepositorio;

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    // Añadir una cancelación
    @Transactional
    public CancelacionDTO registrar(CancelacionDTO cancelacionDTO) {
        //Validamos que exite el id los odio compañeritos
        if (cancelacionDTO.getIdReserva() == null) {
            throw new RuntimeException("Debe especificar una reserva válida");
        }
        Integer idReserva = cancelacionDTO.getIdReserva();

        // Obtener la reserva antes de cancelar para tener los datos para la notificación
        Reserva reserva = reservaRepositorio.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("No existe la reserva con id: " + idReserva));

        //cambia el estado del servicio a cancelada
        reservaServicio.eliminarLogicoReserva(idReserva);

        Cancelacion cancelacion = new Cancelacion();
        cancelacion.setTipoCancelacion(cancelacionDTO.getTipoCancelacion().trim());
        cancelacion.setFCancelacion(Instant.now());
        cancelacion.setReserva(reserva);

        Cancelacion cancelacionGuardada = cancelacionRepositorio.save(cancelacion);

        // 4. NOTIFICACIÓN AL ADMINISTRADOR DE LA SEDE DE LA RESERVA
        if (reserva.getSedeCancha() != null
                && reserva.getSedeCancha().getSede() != null
                && reserva.getSedeCancha().getSede().getUsuario() != null) {

            // Extraemos directamente al usuario Admin asignado a la Sede
            Usuario adminSede = reserva.getSedeCancha().getSede().getUsuario();
            String sedeNombre = reserva.getSedeCancha().getSede().getNombre();

            String mensajeAdmin = String.format("El cliente %s %s ha cancelado su reserva #%d en la sede %s. Motivo: %s.",
                    reserva.getUsuario().getNombre(),
                    reserva.getUsuario().getApellido(),
                    reserva.getIdReserva(),
                    sedeNombre,
                    cancelacionDTO.getTipoCancelacion().trim());

            // Le enviamos la notificación únicamente a su idUsuario
            notificacionServicio.crearNotificacion(
                    adminSede.getIdUsuario(),
                    "Cancelación",
                    "Cancelación en " + sedeNombre,
                    mensajeAdmin
            );
        }
        return modelMapper.map(cancelacionGuardada, CancelacionDTO.class);
    }

    // Listar
    public List<CancelacionDTO> listar() {
        List<CancelacionDTO> lista = cancelacionRepositorio.findAll().stream()
                .map(cancelacion -> modelMapper.map(cancelacion, CancelacionDTO.class))
                .toList();

        if (lista.isEmpty()) {
            throw new RuntimeException("No existen cancelaciones registradas");
        }
        return lista;
    }

    // Listar una cancelación por ID
    public CancelacionDTO listarPorId(Integer id) {
        Cancelacion cancelacion = cancelacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la cancelación con id: " + id));
        return modelMapper.map(cancelacion, CancelacionDTO.class);
    }

    // Eliminar fisico cancelación por ID
    public void eliminar(Integer id) {
        if (!cancelacionRepositorio.existsById(id)) {
            throw new RuntimeException("No existe la cancelación con id: " + id);
        }
        cancelacionRepositorio.deleteById(id);
    }

}
