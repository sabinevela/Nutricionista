package com.itsqmet.nutricional.Repositorio;

import com.itsqmet.nutricional.Entidad.Consultas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConsultasRepositorio extends JpaRepository<Consultas, Long> {
    List<Consultas> findByFechaContainingIgnoreCase(String fecha);
}



