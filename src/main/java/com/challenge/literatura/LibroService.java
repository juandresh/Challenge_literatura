package com.challenge.literatura;

import com.challenge.literatura.api.AuthorData;
import com.challenge.literatura.api.BookData;
import com.challenge.literatura.api.RespuestaApi;
import com.challenge.literatura.model.Autor;
import com.challenge.literatura.model.Libro;
import com.challenge.literatura.repository.AutorRepository;
import com.challenge.literatura.repository.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

@Service
public class LibroService {

    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    public LibroService(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }

    public void listarLibros() {
        var libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.println("\n----------- LIBRO -----------");
                System.out.println(libro);
                System.out.println("-------------------------------");
            });
        }
    }

    public void listarPorIdioma(Scanner sc) {
        System.out.println("""
                
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués""");

        String idioma = sc.nextLine().trim();
        List<String> idiomasValidos = List.of("es", "en", "fr", "pt");

        if (!idiomasValidos.contains(idioma)) {
            System.out.println("\nIdioma no válido.");
            return;
        }

        var libros = libroRepo.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados en ese idioma.");
        } else {
            libros.forEach(libro -> {
                System.out.println("\n----------- LIBRO -----------");
                System.out.println(libro);
                System.out.println("-------------------------------");
            });
        }
    }

    public void buscarLibro(Scanner sc) {
        try {
            System.out.print("Ingrese el título del libro que desea buscar: ");
            String titulo_libro = URLEncoder.encode(sc.nextLine(), StandardCharsets.UTF_8);

            String busqueda = "https://gutendex.com/books/?search=" + titulo_libro;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(busqueda)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            RespuestaApi apiResponse = mapper.readValue(response.body(), RespuestaApi.class);

            if (!apiResponse.getResults().isEmpty()) {
                BookData bookData = apiResponse.getResults().getFirst();
                Autor autorEntidad = null;

                System.out.println("Título: " + bookData.getTitle());

                // Procesar autor
                if (!bookData.getAuthors().isEmpty()) {
                    AuthorData autor = bookData.getAuthors().getFirst();
                    System.out.println("Autor: " + autor.getName() + " (" + autor.getBirthYear() + " - " + autor.getDeathYear() + ")");

                    String[] nombrePartes = autor.getName().split(",", 2);
                    String nombre;
                    String apellido;

                    if (nombrePartes.length == 2) {
                        nombre = nombrePartes[1].trim();
                        apellido = nombrePartes[0].trim();
                    } else{
                        nombre = nombrePartes[0].trim();
                        apellido = null;
                    }

                    autorEntidad = autorRepo.findByNombreAndApellido(nombre, apellido)
                            .orElse(new Autor(nombre, apellido, autor.getBirthYear(), autor.getDeathYear()));

                    if (autorEntidad.getId() == null) {
                        autorRepo.save(autorEntidad);
                    }
                } else {
                    System.out.println("Autor: No hay autor");
                }

                // Procesar idioma
                String idioma;
                if (bookData.getLanguages() != null && !bookData.getLanguages().isEmpty()) {
                    idioma = bookData.getLanguages().get(0);
                    System.out.println("Idioma: " + idioma);
                } else {
                    idioma = "unknown";
                }

                System.out.println("Descargas: " + bookData.getDownloadCount());

                // Procesar libro
                Libro libroEntidad = libroRepo.findByTitulo(bookData.getTitle())
                        .orElse(new Libro(bookData.getTitle(), idioma, bookData.getDownloadCount(), autorEntidad));

                libroRepo.save(libroEntidad);

            } else {
                System.out.println("No se encontró el libro");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
