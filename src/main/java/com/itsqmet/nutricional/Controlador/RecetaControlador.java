package com.itsqmet.nutricional.Controlador;

import com.itextpdf.text.DocumentException;
import com.itsqmet.nutricional.Entidad.Receta;
import com.itsqmet.nutricional.Servicio.RecetaServicio;
import com.itsqmet.nutricional.Repositorio.RecetaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recetas")
public class RecetaControlador {

    @Autowired
    private RecetaRepositorio recetaRepositorio;

    @Autowired
    private RecetaServicio recetaServicio; // Inyección de RecetaServicio

    @GetMapping
    public String mostrarRecetas(Model model) {
        List<Receta> recetas = recetaRepositorio.findAll();
        model.addAttribute("recetas", recetas);
        return "RecetasFormularios/Recetas";
    }

    @GetMapping("/subir")
    public String mostrarFormulario(Model model) {
        model.addAttribute("receta", new Receta());
        return "RecetasFormularios/subir-recetas";
    }

    @PostMapping("/guardar")
    public String guardarReceta(@RequestParam("titulo") String titulo,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("ingredientes") String ingredientes,
                                @RequestParam("pasos") String pasos,
                                @RequestParam("imagen") MultipartFile imagen) {
        try {
            Receta receta = new Receta();
            receta.setTitulo(titulo);
            receta.setDescripcion(descripcion);
            receta.setIngredientes(ingredientes);
            receta.setPasos(pasos);
            if (!imagen.isEmpty()) {
                receta.setImagen(imagen.getBytes());
            }
            recetaRepositorio.save(receta);
            return "redirect:/recetas";
        } catch (IOException e) {
            return "error";
        }
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable Long id) {
        Optional<Receta> recetaOptional = recetaRepositorio.findById(id);
        if (recetaOptional.isPresent() && recetaOptional.get().getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/jpeg");
            return new ResponseEntity<>(recetaOptional.get().getImagen(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recetas/pdf")
    public ResponseEntity<byte[]> descargarPdf() throws DocumentException, IOException {
        byte[] pdf = recetaServicio.generarPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "recetas.pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
