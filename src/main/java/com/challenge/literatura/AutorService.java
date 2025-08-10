package com.challenge.literatura;

import com.challenge.literatura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.stream.Stream;

@Service
public class AutorService {

    private final AutorRepository autorRepo;

    public AutorService(AutorRepository autorRepo) {
        this.autorRepo = autorRepo;
    }

    public void listarAutores() {
        var autores = autorRepo.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(a -> {
                System.out.println("\n" + a.mostrarAutor());
            });
        }
    }

    public void listarAutoresVivos(Scanner sc) {
        System.out.print("\nIngrese el año: ");
        int anio;
        try {
            anio = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
            return;
        }

        var vivosConFecha = autorRepo.findByAnoNacimientoLessThanEqualAndAnoFallecimientoGreaterThanEqual(anio, anio);
        var vivosSinFecha = autorRepo.findByAnoNacimientoLessThanEqualAndAnoFallecimientoIsNull(anio);

        var autores = Stream.concat(vivosConFecha.stream(), vivosSinFecha.stream()).toList();

        if (autores.isEmpty()) {
            System.out.println("No hay registro de autores vivos en ese año.");
        } else {
            autores.forEach(a -> {
                System.out.println("\n" + a.mostrarAutor());
            });
        }
    }
}