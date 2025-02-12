package com.itsqmet.nutricional.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginPControlador {
    @GetMapping("/loginPaciente")
    public String motrarloginPaciente() {
        return "login/loginPaciente";

    }
}
