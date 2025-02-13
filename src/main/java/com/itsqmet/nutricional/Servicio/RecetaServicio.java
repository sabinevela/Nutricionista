package com.itsqmet.nutricional.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.nutricional.Entidad.Pacientes;
import com.itsqmet.nutricional.Entidad.Receta;

import com.itsqmet.nutricional.Repositorio.PacienteRepositorio;
import com.itsqmet.nutricional.Repositorio.RecetaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaServicio {

    @Autowired
    RecetaRepositorio recetaRepositorio;

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    public List<Receta> mostrarReceta() {
        return recetaRepositorio.findAll();
    }

    public List<Receta> buscarRecetaNombre(String buscarReceta) {
        if (buscarReceta == null || buscarReceta.isEmpty()) {
            return recetaRepositorio.findAll();
        } else {
            return recetaRepositorio.findByTituloContainingIgnoreCase(buscarReceta);
        }
    }

    public void eliminarReceta(Long id) {
        recetaRepositorio.deleteById(id);
    }

    public Optional<Receta> buscarRecetaId(Long id) {
        return recetaRepositorio.findById(id);
    }

    @Transactional
    public void crearReceta1(Receta receta) {
        Pacientes paciente = pacienteRepositorio.findById(receta.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        receta.setPaciente(paciente);
        recetaRepositorio.save(receta);
    }

    public void guardarReceta(Receta receta, MultipartFile imagen) {
        try {
            if (!imagen.isEmpty()) {
                receta.setImagen(imagen.getBytes());
            }
            recetaRepositorio.save(receta);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    public byte[] generarPdf() throws DocumentException, IOException {
        List<Receta> recetas = recetaRepositorio.findAll();
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Lista de Recetas", FontFactory.getFont("Arial", 14, Font.BOLD)));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.addCell(new PdfPCell(new Phrase("Título", FontFactory.getFont("Arial", 12))));
        table.addCell(new PdfPCell(new Phrase("Ingredientes", FontFactory.getFont("Arial", 12))));
        table.addCell(new PdfPCell(new Phrase("Pasos", FontFactory.getFont("Arial", 12))));
        table.addCell(new PdfPCell(new Phrase("Descripcion", FontFactory.getFont("Arial", 12))));

        for (Receta receta : recetas) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(receta.getTitulo()), FontFactory.getFont("Arial", 11))));
            table.addCell(new PdfPCell(new Phrase(receta.getIngredientes(), FontFactory.getFont("Arial", 11))));
            table.addCell(new PdfPCell(new Phrase(receta.getPasos(), FontFactory.getFont("Arial", 11))));
            table.addCell(new PdfPCell(new Phrase(receta.getDescripcion(), FontFactory.getFont("Arial", 11))));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }
}
