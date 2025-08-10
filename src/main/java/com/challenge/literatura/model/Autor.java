package com.challenge.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private Integer anoNacimiento;
    private Integer anoFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    public Autor(String nombre, String apellido, Integer ano_nacimiento, Integer ano_fallecimiento){
        this.nombre = nombre;
        this.apellido = apellido;
        this.anoNacimiento = ano_nacimiento;
        this.anoFallecimiento = ano_fallecimiento;
    }

    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    public void removeLibro(Libro libro) {
        libros.remove(libro);
        libro.setAutor(null);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getAno_nacimiento() {
        return anoNacimiento;
    }

    public void setAno_nacimiento(Integer ano_nacimiento) {
        this.anoNacimiento = ano_nacimiento;
    }

    public Integer getAno_fallecimiento() {
        return anoFallecimiento;
    }

    public void setAno_fallecimiento(Integer ano_fallecimiento) {
        this.anoFallecimiento = ano_fallecimiento;
    }

    @Override
    public String toString() {
        if (apellido != null) {
            return apellido + ", " + nombre + " (" + anoNacimiento + "-" + anoFallecimiento + ")";
        } else {
            return nombre + " (" + anoNacimiento + "-" + anoFallecimiento + ")";
        }
    }

    public String mostrarAutor() {

        List<String> listLibros = new ArrayList<>();

        for (Libro libro : libros) {
            listLibros.add(libro.getTitulo());
        }

        if (apellido == null) {
            return "Autor: " + nombre +
                    "\nFecha de nacimiento: " + anoNacimiento +
                    "\nFecha de fallecimiento: " + anoFallecimiento +
                    "\nLibros: " + listLibros;
        }else{
            return "Autor: " + apellido + ", " + nombre +
                    "\nFecha de nacimiento: " + anoNacimiento +
                    "\nFecha de fallecimiento: " + anoFallecimiento +
                    "\nLibros: " + listLibros;
        }
    }
}
