package com.itsqmet.nutricional.Controlador;

import com.itsqmet.nutricional.Entidad.Consultas;
import com.itsqmet.nutricional.Entidad.Dietas;
import com.itsqmet.nutricional.Entidad.Nutriologo;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Servicio.ConsultasServicio;
import com.itsqmet.nutricional.Servicio.DietaServicio;
import com.itsqmet.nutricional.Servicio.NutriologoServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class NutriologoControlador {

    @Autowired
    private NutriologoServicio nutriologoServicio;

    @Autowired
    ConsultasServicio consultasServicio;

    @Autowired
    DietaServicio dietaServicio;

    @GetMapping("/nutrologo")
    public String listarNutrologo(@RequestParam(name="buscarNutrologo", required = false, defaultValue = "") String buscarNutrologo, Model model){
        List<Nutriologo> nutriologo = nutriologoServicio.buscarNutrologoNombre(buscarNutrologo);
        model.addAttribute("buscarNutrologo", buscarNutrologo);
        model.addAttribute("nutrologo", nutriologo);
        return "listas/listaPaciente";
    }

    @GetMapping("/formularioNutrologo")
    public String mostrarFormurario(Model model){
        model.addAttribute("nutriologo", new Nutriologo());
        return "datosguardados/formulario";
    }

    @PostMapping("/registrarNutriologo")
    public String insertarNutriologo(Nutriologo nutriologo){
        nutriologoServicio.guardarNutrologo(nutriologo);
        return "redirect:/paciente";
    }

    @GetMapping("/actualizarNutriologo/{id}")
    public String editarNutriologo(@PathVariable Long id, Model model){
        Optional<Nutriologo> nutriologo = nutriologoServicio.buscarNutrologoId(id);
        model.addAttribute("nutriologo", nutriologo);
        return "datosguardados/formulario";
    }

    @GetMapping("/eliminarNutriologo/{id}")
    public String eliminarNutriologo(@PathVariable Long id){
        nutriologoServicio.buscarNutrologoId(id);
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

    @GetMapping("/consultasNutriologo/{id}")
    public String obtenerConsultasPorNutriologo(@PathVariable Long id, Model model){
        Optional<Consultas> consulta = consultasServicio.obtenerConsultaPorId(id);
        Nutriologo nutriologo = nutriologoServicio.buscarNutrologoId(id).orElseThrow(() -> new RuntimeException("Nutrologo no encontrado"));
        model.addAttribute("Nutriologo", nutriologo);
        model.addAttribute("consulta", consulta);

        return "datosguardados/formularioExitoso";
    }

    @GetMapping("/nutriologoDieta/{id}")
    public String obtenerNutrologoPorDieta(@PathVariable Long id, Model model){
        Optional<Dietas> dieta = dietaServicio.buscarDietaId(id);
        Nutriologo nutriologo = nutriologoServicio.buscarNutrologoId(id).orElseThrow(() -> new RuntimeException("Dieta no encontrado"));
        model.addAttribute("nutriologo", nutriologo);
        model.addAttribute("dieta", dieta);

        return "datosguardados/formularioDieta";
    }
}
