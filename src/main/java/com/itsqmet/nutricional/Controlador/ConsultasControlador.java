package com.itsqmet.nutricional.Controlador;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Servicio.ConsultasServicio;
import com.itsqmet.nutricional.Servicio.NutriologoServicio;
import com.itsqmet.nutricional.Servicio.PacienteServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Controller
public class ConsultasControlador {


    @Autowired
    ConsultasServicio consultasServicio;

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    NutriologoServicio nutriologoServicio;


    @GetMapping("/consultas")
    public String listarConsultas(@RequestParam(name="buscarConsulta", required = false, defaultValue = "") String buscarConsulta, Model model){
        List<Consultas> consulta = consultasServicio.buscarConsultaPorFecha(buscarConsulta);
        model.addAttribute("buscarConsulta", buscarConsulta);
        model.addAttribute("consulta", consulta);
        return "listas/listaConsultas";
    }

    @GetMapping("/formularioConsulta")
    public String mostrarFormulario(Model model) {
        List<Pacientes> pacientes = pacienteServicio.mostrarPacientes();
        List<Nutriologo> nutriologo = nutriologoServicio.mostrarNutrologo();
        model.addAttribute("consulta", new Consultas());
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("nutrologo", nutriologo);
        return "consulta/formularioConsulta";
    }

    @PostMapping("/registrarConsulta")
    public String insertarConsulta(Consultas consulta){
        consultasServicio.crearConsulta(consulta);
        return "redirect:/consultas";
    }

    @GetMapping("/actualizarConsulta/{id}")
    public String editarConsultas(@PathVariable Long id, Model model){
            Optional<Consultas> consulta = consultasServicio.obtenerConsultaPorId(id);
        model.addAttribute("consulta", consulta);
        model.addAttribute("pacientes", pacienteServicio.mostrarPacientes());
        model.addAttribute("nutrologo", nutriologoServicio.mostrarNutrologo());
        return "consulta/formularioConsulta";
    }

    @GetMapping("/eliminarConsulta/{id}")
    public String eliminarlibro(@PathVariable Long id){
        consultasServicio.eliminarConsulta(id);
        return "redirect:/consultas";
    }
}
