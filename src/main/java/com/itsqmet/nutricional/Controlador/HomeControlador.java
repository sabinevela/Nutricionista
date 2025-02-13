package com.itsqmet.nutricional.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControlador {

    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }
    @GetMapping("/reseñas")
    public String mostrarReseñas(){
        return "comentarios/reseñasPacientes";
    }
}