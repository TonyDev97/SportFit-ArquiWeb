package com.upc.sporfit.servicios;

import com.upc.sporfit.entidades.TipoCancha;
import com.upc.sporfit.repositorios.CanchaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanchaServicio {
    @Autowired
    private CanchaRepositorio canchaRepositorio;

    public TipoCancha insertar(TipoCancha tipoCancha) { return canchaRepositorio.save(tipoCancha);}

    public List<TipoCancha> listar(){ return canchaRepositorio.findAll();}

    public TipoCancha actualizar (Long id , TipoCancha datosActualizados){
        // 1. Buscamos si existe en la BD
        TipoCancha canchaExistente = canchaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Cancha no encontrada con ID " + id));

        // 2. Modificamos los datos permitidos usando los Setters de Lombok
        canchaExistente.setNombre(datosActualizados.getNombre());
        canchaExistente.setAforo(datosActualizados.getAforo());
        // canchaExistente.setPrecio(datosActualizados.getPrecio()); // Descomenta cuando agregues el precio

        // 3. Guardamos los cambios
        return canchaRepositorio.save(canchaExistente);
    }

    public void eliminar(Long id) {
        // ELIMINACIÓN FÍSICA TEMPORAL PARA PRUEBAS
        canchaRepositorio.deleteById(id);

        /*
         * LÓGICA FUTURA (Obligatoria para la evaluación del RF05):
         * El requerimiento RF05 exige que la desactivación sea una baja lógica[cite: 1].
         *
         * TipoCancha cancha = canchaRepositorio.findById(id).orElseThrow();
         * cancha.setEstado("Inactivo");
         * canchaRepositorio.save(cancha);
         */
    }

}
