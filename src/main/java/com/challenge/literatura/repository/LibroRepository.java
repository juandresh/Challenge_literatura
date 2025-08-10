package com.challenge.literatura.repository;

import com.challenge.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String nombre);

    List<Libro> findByIdioma(String idioma);
}