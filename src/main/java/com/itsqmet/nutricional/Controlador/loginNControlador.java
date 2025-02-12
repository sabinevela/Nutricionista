package com.itsqmet.nutricional.Controlador;

import com.itsqmet.nutricional.Entidad.Nutriologo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginNControlador {
    @GetMapping("/loginNutri")
    public String mostrarloginNutriologo(Model model) {
        model.addAttribute("nutriologo", new Nutriologo()); // Agrega un objeto Usuario al modelo
        return "login/loginNutriologo";
    }
}

