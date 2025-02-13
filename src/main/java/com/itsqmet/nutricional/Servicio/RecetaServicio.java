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

    public byte[] generarPdfPorReceta(Long recetaId) throws DocumentException, IOException {
        Receta receta = recetaRepositorio.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Receta: " + receta.getTitulo(), FontFactory.getFont("Arial", 14, Font.BOLD)));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell(new PdfPCell(new Phrase("Campo", FontFactory.getFont("Arial", 12))));
        table.addCell(new PdfPCell(new Phrase("Detalle", FontFactory.getFont("Arial", 12))));

        table.addCell(new PdfPCell(new Phrase("Título", FontFactory.getFont("Arial", 11))));
        table.addCell(new PdfPCell(new Phrase(receta.getTitulo(), FontFactory.getFont("Arial", 11))));

        table.addCell(new PdfPCell(new Phrase("Ingredientes", FontFactory.getFont("Arial", 11))));
        table.addCell(new PdfPCell(new Phrase(receta.getIngredientes(), FontFactory.getFont("Arial", 11))));

        table.addCell(new PdfPCell(new Phrase("Pasos", FontFactory.getFont("Arial", 11))));
        table.addCell(new PdfPCell(new Phrase(receta.getPasos(), FontFactory.getFont("Arial", 11))));

        table.addCell(new PdfPCell(new Phrase("Descripción", FontFactory.getFont("Arial", 11))));
        table.addCell(new PdfPCell(new Phrase(receta.getDescripcion(), FontFactory.getFont("Arial", 11))));

        if (receta.getImagen() != null) {
            Image img = Image.getInstance(receta.getImagen());
            img.scaleAbsolute(150, 150); // Ajustamos el tamaño a 150x150 px
            PdfPCell imageCell = new PdfPCell(img, true);
            imageCell.setColspan(2);
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            imageCell.setPadding(10); // Agregamos un pequeño margen
            imageCell.setBorder(Rectangle.NO_BORDER); // Eliminamos el borde de la celda
            table.addCell(imageCell);
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }

}