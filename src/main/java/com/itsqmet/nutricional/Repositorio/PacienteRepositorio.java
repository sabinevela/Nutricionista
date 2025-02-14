package com.itsqmet.nutricional.Repositorio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepositorio extends JpaRepository<Pacientes, Long> {
    List<Pacientes> findByNombreContainingIgnoreCase(String nombre);
    List<Consultas> findByConsultaId(Long consulta_id);
    Pacientes findByEmail(String email);
    List<Dietas> findByRecetaId(Long Receta_id);
}
