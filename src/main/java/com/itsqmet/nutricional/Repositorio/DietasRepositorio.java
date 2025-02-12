package com.itsqmet.nutricional.Repositorio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DietasRepositorio extends JpaRepository<Dietas, Long> {
    List<Dietas> findByNombreContainingIgnoreCase(String nombre);
}
