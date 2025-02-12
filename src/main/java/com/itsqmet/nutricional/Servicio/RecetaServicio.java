package com.itsqmet.nutricional.Servicio;


import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Entidad.Receta;

import com.itsqmet.nutricional.Repositorio.PacienteRepositorio;
import com.itsqmet.nutricional.Repositorio.RecetaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaServicio {

    @Autowired
    RecetaRepositorio recetaRepositorio;

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    public List<Receta> mostrarReceta() {
        return recetaRepositorio.findAll();
    }

    public List<Receta> buscarRecetaNombre(String buscarReceta) {
        if (buscarReceta == null || buscarReceta.isEmpty()) {
            return recetaRepositorio.findAll();
        } else {
            return recetaRepositorio.findByTituloContainingIgnoreCase(buscarReceta);
        }
    }

    public void eliminarReceta(Long id) {
        recetaRepositorio.deleteById(id);
    }

    public Optional<Receta> buscarRecetaId(Long id) {
        return recetaRepositorio.findById(id);
    }

    @Transactional
    public void crearReceta1(Receta receta) {

        Pacientes paciente = pacienteRepositorio.findById(receta.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        receta.setPaciente(paciente);
        recetaRepositorio.save(receta);
    }

    public void guardarReceta(Receta receta, MultipartFile imagen) {
        try {
            if (!imagen.isEmpty()) {
                receta.setImagen(imagen.getBytes());
            }
            recetaRepositorio.save(receta);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}
