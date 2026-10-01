package com.upc.sportfit.servicios;

import com.upc.sportfit.entidades.SedeCancha;
import com.upc.sportfit.repositorios.SedeCanchaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.sportfit.dtos.SedeCanchaDTO;
import com.upc.sportfit.repositorios.SedeRepositorio;
import com.upc.sportfit.repositorios.TipoCanchaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import java.util.List;

@Service
public class SedeCanchaServicio {

    @Autowired
    private SedeCanchaRepositorio sedeCanchaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SedeRepositorio sedeRepositorio;

    @Autowired
    private TipoCanchaRepositorio tipoCanchaRepositorio;

    public List<SedeCancha> listarPorSedeActivas(Integer idSede) {
        return sedeCanchaRepositorio.encontrarActivasPorSede(idSede);
    }

    public List<SedeCancha> listarPorSedeYDeporte(Integer idSede, String deporte) {
        return sedeCanchaRepositorio.encontrarActivasPorSedeYDeporte(idSede, deporte);
    }

    public List<SedeCancha> listarTodasPorSede(Integer idSede) {
        return sedeCanchaRepositorio.encontrarTodasPorSede(idSede);
    }

    public List<SedeCancha> listar() {
        return sedeCanchaRepositorio.findAll();
    }

    public SedeCancha buscarPorId(Integer id) {
        return sedeCanchaRepositorio.findById(id).orElse(null);
    }

    public SedeCancha insertar(SedeCancha sedeCancha) {
        return sedeCanchaRepositorio.save(sedeCancha);
    }

    public SedeCancha actualizar(SedeCancha sedeCancha) {
        return sedeCanchaRepositorio.save(sedeCancha);
    }

    public void eliminar(Integer id) {
        sedeCanchaRepositorio.deleteById(id);
    }

    // HU14: consultar espacios deportivos utilizando DTO
    public List<SedeCanchaDTO> listarEspacios() {
        return sedeCanchaRepositorio.findAll()
                .stream()
                .map(cancha -> modelMapper.map(
                        cancha, SedeCanchaDTO.class))
                .toList();
    }

    // HU14: consultar un espacio deportivo
    public SedeCanchaDTO buscarEspacio(Integer id) {
        return sedeCanchaRepositorio.findById(id)
                .map(cancha -> modelMapper.map(
                        cancha, SedeCanchaDTO.class))
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe la cancha con ID " + id
                ));
    }

    // HU14: actualizar la información del espacio deportivo
    @Transactional
    public SedeCanchaDTO actualizarEspacio(
            Integer id,
            SedeCanchaDTO dto) {

        SedeCancha cancha = sedeCanchaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe la cancha con ID " + id
                ));

        if (dto.getNombre() == null
                || dto.getNombre().trim().isEmpty()
                || dto.getNombre().trim().length() > 50) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio y admite hasta 50 caracteres"
            );
        }

        if (dto.getPrecio() == null
                || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0
                || dto.getPrecio().compareTo(
                new BigDecimal("99999999.99")) > 0
                || dto.getPrecio().scale() > 2) {
            throw new IllegalArgumentException(
                    "El precio debe ser positivo, tener hasta dos decimales "
                            + "y no superar 99999999.99"
            );
        }

        if (dto.getEstado() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el estado de la cancha"
            );
        }

        if (dto.getImgCancha() == null
                || dto.getImgCancha().trim().isEmpty()
                || dto.getImgCancha().length() > 300) {
            throw new IllegalArgumentException(
                    "Debe indicar una URL de imagen de hasta 300 caracteres"
            );
        }

        if (dto.getSede() == null
                || dto.getSede().getIdSede() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar la sede"
            );
        }

        if (dto.getCancha() == null
                || dto.getCancha().getIdTipoCancha() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el tipo de cancha"
            );
        }

        cancha.setSede(
                sedeRepositorio.findById(dto.getSede().getIdSede())
                        .orElseThrow(() -> new NoSuchElementException(
                                "La sede no existe"
                        ))
        );

        cancha.setCancha(
                tipoCanchaRepositorio
                        .findById(dto.getCancha().getIdTipoCancha())
                        .orElseThrow(() -> new NoSuchElementException(
                                "El tipo de cancha no existe"
                        ))
        );

        cancha.setNombre(dto.getNombre().trim());
        cancha.setPrecio(dto.getPrecio());
        cancha.setEstado(dto.getEstado());
        cancha.setImgCancha(dto.getImgCancha());

        cancha = sedeCanchaRepositorio.save(cancha);

        return modelMapper.map(cancha, SedeCanchaDTO.class);
    }

    @Transactional
    public SedeCanchaDTO cambiarEstadoEspacio(
            Integer id,
            Boolean estado) {

        if (estado == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el estado"
            );
        }

        SedeCancha cancha = sedeCanchaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe la cancha con ID " + id
                ));

        cancha.setEstado(estado);
        cancha = sedeCanchaRepositorio.save(cancha);

        return modelMapper.map(cancha, SedeCanchaDTO.class);
    }
}