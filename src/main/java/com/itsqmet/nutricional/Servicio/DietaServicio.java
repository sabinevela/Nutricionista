package com.itsqmet.nutricional.Servicio;


import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Repositorio.DietasRepositorio;
import com.itsqmet.nutricional.Repositorio.NutriologoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietaServicio {
    @Autowired
    DietasRepositorio dietasRepositorio;

    @Autowired
    NutriologoRepositorio nutriologoRepositorio;

    public List<Dietas> mostrarDietas() {
        return dietasRepositorio.findAll();
    }

    public List<Dietas> buscarDietaNombre(String buscarDieta) {
        if (buscarDieta == null || buscarDieta.isEmpty()) {
            return dietasRepositorio.findAll();
        } else {
            return dietasRepositorio.findByNombreContainingIgnoreCase(buscarDieta);
        }
    }

    public void guardarDieta(Dietas dieta) {
        dietasRepositorio.save(dieta);
    }

    public void eliminarDieta(Long id) {
        dietasRepositorio.deleteById(id);
    }

    public Optional<Dietas> buscarDietaId(Long id) {
        return dietasRepositorio.findById(id);
    }

    @Transactional
    public void crearDieta1(Dietas dieta) {

        Nutriologo nutriologo = nutriologoRepositorio.findById(dieta.getNutriologo().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        dieta.setNutriologo(nutriologo);
        dietasRepositorio.save(dieta);
    }

}

