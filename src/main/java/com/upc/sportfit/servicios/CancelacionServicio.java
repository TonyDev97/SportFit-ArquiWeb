package com.upc.sportfit.servicios;

import com.upc.sportfit.dtos.CancelacionDTO;
import com.upc.sportfit.entidades.Cancelacion;
import com.upc.sportfit.repositorios.CancelacionRepositorio;
import com.upc.sportfit.repositorios.ReservaRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    private ModelMapper modelMapper;

    // Añadir una cancelación
    @Transactional
    public CancelacionDTO registrar(CancelacionDTO cancelacionDTO) {
        if (cancelacionDTO.getReserva() == null || cancelacionDTO.getReserva().getIdReserva() == null) {
            throw new RuntimeException("Debe especificar una reserva válida");
        }
        Integer idReserva = cancelacionDTO.getReserva().getIdReserva();

        reservaServicio.eliminarLogicoReserva(idReserva);

        Cancelacion cancelacion = modelMapper.map(cancelacionDTO, Cancelacion.class);
        cancelacion.setIdCancelacion(null);

        Cancelacion cancelacionGuardada = cancelacionRepositorio.save(cancelacion);
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
