package com.itsqmet.nutricional.Repositorio;


import com.itsqmet.nutricional.Entidad.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, Long> {
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
}



