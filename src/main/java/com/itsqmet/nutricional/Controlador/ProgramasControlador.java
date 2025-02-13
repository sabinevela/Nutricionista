package com.itsqmet.nutricional.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgramasControlador {
    @GetMapping("/programas")
    private String mostrarProgramas(){
        return "mostrar/programas";
    }
}
