package com.itsqmet.nutricional.Entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nutriologos")
public class Nutriologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Min(value = 18, message = "La edad debe ser mayor a 18 años")
    private int edad;

    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    private int experiencia;

    @OneToMany(mappedBy = "nutriologo", fetch = FetchType.LAZY)
    private List<Consultas> consulta = new ArrayList<>();

    @OneToMany(mappedBy = "nutriologo", fetch = FetchType.LAZY)
    private List<Dietas> dieta = new ArrayList<>();


    public List<Dietas> getDieta() {
        return dieta;
    }

    public void setDieta(List<Dietas> dieta) {
        this.dieta = dieta;
    }

    public List<Consultas> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Consultas> consulta) {
        this.consulta = consulta;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }
}
