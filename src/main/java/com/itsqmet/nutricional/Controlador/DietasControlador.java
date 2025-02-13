package com.itsqmet.nutricional.Controlador;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Servicio.DietaServicio;
import com.itsqmet.nutricional.Servicio.NutriologoServicio;
import com.itsqmet.nutricional.Servicio.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class DietasControlador {

    @Autowired
    DietaServicio dietaServicio;

    @Autowired
    NutriologoServicio nutriologoServicio;

    @GetMapping("/dieta")
    public String listarDieta(@RequestParam(name="buscarDieta", required = false, defaultValue = "") String buscarDieta, Model model){
        List<Dietas> dieta = dietaServicio.buscarDietaNombre(buscarDieta);
        model.addAttribute("buscarDieta", buscarDieta);
        model.addAttribute("dieta", dieta);
        return "listas/listaDieta";
    }

    @GetMapping("/formularioDieta")
    public String mostrarFormurario(Model model){
        List<Nutriologo> nutriologos = nutriologoServicio.mostrarNutrologo();
        model.addAttribute("dieta", new Dietas());
        model.addAttribute("nutriologos", nutriologos);
        return "datosdietas/dietaFormulario";
    }

    @PostMapping("/registrarDieta")
    public String insertarDieta(Dietas dieta){
        dietaServicio.guardarDieta(dieta);
        return "redirect:/dieta";
    }

    @GetMapping("/actualizarDieta/{id}")
    public String editarDieta(@PathVariable Long id, Model model){
        Optional<Dietas> dieta = dietaServicio.buscarDietaId(id);
        model.addAttribute("nutriologos", nutriologoServicio.mostrarNutrologo());
        model.addAttribute("dieta", dieta);
        return "datosdietas/dietaFormulario";
    }

    @GetMapping("/eliminarDieta/{id}")
    public String eliminarDieta(@PathVariable Long id){
        dietaServicio.eliminarDieta(id);
        return "redirect:/dieta";
    }

}
