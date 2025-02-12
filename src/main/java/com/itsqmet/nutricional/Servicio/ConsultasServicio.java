package com.itsqmet.nutricional.Servicio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Repositorio.ConsultasRepositorio;
import com.itsqmet.nutricional.Repositorio.PacienteRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  ConsultasServicio {

    @Autowired
    private ConsultasRepositorio consultasRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    public List<Consultas> buscarConsultaPorFecha(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return consultasRepositorio.findAll();
        } else {
            return consultasRepositorio.findByFechaContainingIgnoreCase(fecha);
        }
    }

    public Consultas crearConsulta(Consultas consulta) {
        return consultasRepositorio.save(consulta);
    }

    public List<Consultas> obtenerConsultas() {
        return consultasRepositorio.findAll();
    }

    public Optional<Consultas> obtenerConsultaPorId(Long id) {
        return consultasRepositorio.findById(id);
    }

    public void eliminarConsulta(Long id){
        consultasRepositorio.deleteById(id);
    }

    @Transactional
    public void crearConsulta1(Consultas consulta) {

        Pacientes paciente = pacienteRepositorio.findById(consulta.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        consulta.setPaciente(paciente);
        consultasRepositorio.save(consulta);
    }

}

