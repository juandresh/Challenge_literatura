package com.challenge.literatura.model;
import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private float descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro (){}

    public Libro (String titulo, String idioma, float descargas, Autor autor){
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public float getDescargas() {
        return descargas;
    }

    public void setDescargas(float descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                "\nAutor: " + autor +
                "\nIdioma: " + idioma +
                "\nDescargas: " + descargas;
    }
}
