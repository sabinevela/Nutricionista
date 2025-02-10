package com.itsqmet.nutricional.Entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dietas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la dieta no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de la dieta debe tener entre 3 y 50 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres.")
    private String descripcion;

    @NotNull(message = "Las calorías no pueden estar vacías.")
    @Min(value = 800, message = "Las calorías deben ser al menos 800.")
    @Max(value = 5000, message = "Las calorías no pueden superar 5000.")
    private Integer calorias;

    @NotBlank(message = "Debe especificar el día de la semana.")
    @Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)$",
            message = "El día debe ser un día válido de la semana.")
    private String dia;

    @NotBlank(message = "Debe seleccionar un tipo de comida.")
    @Pattern(regexp = "^(Desayuno|Almuerzo|Cena|Merienda)$",
            message = "El tipo de comida debe ser Desayuno, Almuerzo, Cena o Merienda.")
    private String tipoComida;

    @NotBlank(message = "Debe incluir al menos un alimento en la dieta.")
    @Size(min = 5, message = "La lista de alimentos debe tener al menos 5 caracteres.")
    private String alimentos;

    @NotNull(message = "Debe indicar la duración en días.")
    @Min(value = 1, message = "La dieta debe durar al menos 1 día.")
    @Max(value = 30, message = "La dieta no puede durar más de 30 días.")
    private Integer duracion;

    @ManyToOne
    @JoinColumn(name = "idNutrologo")
    private Nutriologo nutriologo;

    public Nutriologo getNutriologo() {
        return nutriologo;
    }

    public void setNutriologo(Nutriologo nutriologo) {
        this.nutriologo = nutriologo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public String getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(String alimentos) {
        this.alimentos = alimentos;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
