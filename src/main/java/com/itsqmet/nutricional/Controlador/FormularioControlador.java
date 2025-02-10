package com.itsqmet.nutricional.Controlador;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Entidad.Receta;
import com.itsqmet.nutricional.Servicio.ConsultasServicio;
import com.itsqmet.nutricional.Servicio.DietaServicio;
import com.itsqmet.nutricional.Servicio.PacienteServicio;
import com.itsqmet.nutricional.Servicio.RecetaServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FormularioControlador {

    @Autowired
    PacienteServicio pacienteServicio;

    @Autowired
    ConsultasServicio consultasServicio;

    @Autowired
    RecetaServicio recetaServicio;

    //Leer los autores
    @GetMapping("/paciente")
    public String listarPaciente(@RequestParam(name="buscarPaciente", required = false, defaultValue = "") String buscarPaciente, Model model){
        List<Pacientes> paciente = pacienteServicio.buscarPacienteNombre(buscarPaciente);
        model.addAttribute("buscarPaciente", buscarPaciente);
        model.addAttribute("paciente", paciente);
        return "listas/listaPaciente";
    }

    @GetMapping("/formulario")
    public String mostrarFormurario(Model model){
        model.addAttribute("paciente", new Pacientes());
        return "datosguardados/formulario";
    }

    @PostMapping("/registrar")
    public String insertarPaciente(Pacientes paciente){
        pacienteServicio.guardarPaciente(paciente);
        return "redirect:/paciente";
    }

    @GetMapping("/actualizar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model){
        Optional<Pacientes> paciente = pacienteServicio.buscarPacienteId(id);
        model.addAttribute("paciente", paciente);
        return "datosguardados/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id){
        pacienteServicio.eliminarPaciente(id);
        return "redirect:/paciente";
    }

    //@GetMapping("/autores/pdf")
    //public ResponseEntity<byte[]> decargarpdf() throws Exception{
       // byte[] pdf = autorServicio.generarpdf();
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_PDF);
        //headers.setContentDispositionFormData("attachment", "autores.pdf");
        //return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    //}

    @GetMapping("/consultas/{id}")
    public String obtenerConsultasPorPaciente(@PathVariable Long id, Model model){
        Optional<Consultas> consulta = consultasServicio.obtenerConsultaPorId(id);
        Pacientes paciente = pacienteServicio.buscarPacienteId(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        model.addAttribute("paciente", paciente);
        model.addAttribute("consulta", consulta);

        return "datosguardados/formularioExitoso";
    }

    @GetMapping("/pacienteDieta/{id}")
    public String obtenerPacintesPorDieta(@PathVariable Long id, Model model){
        Optional<Receta> receta = recetaServicio.buscarRecetaId(id);
        Pacientes paciente = pacienteServicio.buscarPacienteId(id).orElseThrow(() -> new RuntimeException("Receta no encontrado"));
        model.addAttribute("paciente", paciente);
        model.addAttribute("receta", receta);

        return "datosguardados/formularioDieta";
    }




}
