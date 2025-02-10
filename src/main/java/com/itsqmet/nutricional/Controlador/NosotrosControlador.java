package com.itsqmet.nutricional.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class NosotrosControlador {
    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "Nosotros/nosotros";
    }
}
