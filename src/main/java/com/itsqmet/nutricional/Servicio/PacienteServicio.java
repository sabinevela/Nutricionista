package com.itsqmet.nutricional.Servicio;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Entidad.Receta;
import com.itsqmet.nutricional.Repositorio.ConsultasRepositorio;
import com.itsqmet.nutricional.Repositorio.PacienteRepositorio;
import com.itsqmet.nutricional.Repositorio.RecetaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;



@Service
public class PacienteServicio implements UserDetailsService {
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Autowired
    ConsultasRepositorio consultasRepositorio;

    @Autowired
    RecetaRepositorio recetaRepositorio;

    public List<Pacientes> mostrarPacientes(){
        return pacienteRepositorio.findAll();
    }

    public List<Pacientes> buscarPacienteNombre(String buscarPaciente){
        if(buscarPaciente == null || buscarPaciente.isEmpty()){
            return pacienteRepositorio.findAll();
        }else{
            return pacienteRepositorio.findByNombreContainingIgnoreCase(buscarPaciente);
        }
    }

    public void guardarPaciente(Pacientes paciente){
        pacienteRepositorio.save(paciente);
    }

    public void eliminarPaciente(Long id){
        pacienteRepositorio.deleteById(id);
    }

    public Optional<Pacientes> buscarPacienteId(Long id){
        return pacienteRepositorio.findById(id);
    }

    @Transactional
    public List<Consultas> obtenerConsultasPorFecha(String fecha) {
        return consultasRepositorio.findByFechaContainingIgnoreCase(fecha);
    }

    @Transactional
    public List<Receta> obtenerRecetaPorTitulo(String titulo) {
        return recetaRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pacientes paciente = pacienteRepositorio.findByEmail(email);
        if (paciente == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.withUsername(paciente.getEmail())
                .password(paciente.getPassword())
                .roles(paciente.getRole().toUpperCase())
                .build();
    }

}
