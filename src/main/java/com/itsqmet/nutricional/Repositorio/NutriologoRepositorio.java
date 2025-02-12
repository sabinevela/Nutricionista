package com.itsqmet.nutricional.Repositorio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Entidad.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutriologoRepositorio extends JpaRepository<Nutriologo, Long> {
    List<Nutriologo> findByNombreContainingIgnoreCase(String nombre);
    List<Consultas> findByConsultaId(Long consulta_id);
    List<Dietas> findByDietaId(Long dieta_id);
}


