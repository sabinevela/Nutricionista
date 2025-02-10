package com.itsqmet.nutricional.Servicio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Repositorio.ConsultasRepositorio;
import com.itsqmet.nutricional.Repositorio.DietasRepositorio;
import com.itsqmet.nutricional.Repositorio.NutriologoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutriologoServicio {

    @Autowired
    NutriologoRepositorio nutriologoRepositorio;

    @Autowired
    ConsultasRepositorio consultasRepositorio;

    @Autowired
    DietasRepositorio dietasRepositorio;

    public List<Nutriologo> mostrarNutrologo(){
        return nutriologoRepositorio.findAll();
    }

    public List<Nutriologo> buscarNutrologoNombre(String buscarNutrologo){
        if(buscarNutrologo == null || buscarNutrologo.isEmpty()){
            return nutriologoRepositorio.findAll();
        }else{
            return nutriologoRepositorio.findByNombreContainingIgnoreCase(buscarNutrologo);
        }
    }

    public void guardarNutrologo(Nutriologo nutriologo){
        nutriologoRepositorio.save(nutriologo);
    }

    public void eliminarNutrologo(Long id){
        nutriologoRepositorio.deleteById(id);
    }

    public Optional<Nutriologo> buscarNutrologoId(Long id){
        return nutriologoRepositorio.findById(id);
    }

    @Transactional
    public List<Consultas> obtenerConsultasPorFecha(String fecha) {
        return consultasRepositorio.findByFechaContainingIgnoreCase(fecha);
    }

    @Transactional
    public List<Dietas> obtenerDietaPorFecha(String nombre) {
        return dietasRepositorio.findByNombreContainingIgnoreCase(nombre);
    }

}
