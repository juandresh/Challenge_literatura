package com.challenge.literatura;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class MenuService {

    private final LibroService libroService;
    private final AutorService autorService;

    public MenuService(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
            System.out.println("Elija la opción a través de su número:");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1 -> libroService.buscarLibro(sc);
                case 2 -> libroService.listarLibros();
                case 3 -> autorService.listarAutores();
                case 4 -> autorService.listarAutoresVivos(sc);
                case 5 -> libroService.listarPorIdioma(sc);
                case 0 -> System.out.println("Hasta luego :)");
                default -> System.out.println("Ingrese una opción válida.\n");
            }
        }
    }
}

/*
@Component
public class MenuService {

    private final AutorRepository autorRepo;
    private final LibroRepository libroRepo;

    public MenuService(AutorRepository autorRepo, LibroRepository libroRepo) {
        this.autorRepo = autorRepo;
        this.libroRepo = libroRepo;

        mostrarMenu();
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion = -10;

        while (opcion != 0) {
            System.out.println("\n-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
            System.out.println("Elija la opción a través de su número.");
            System.out.println("1 - Buscar libro por título.");
            System.out.println("2 - Listar libros resgistrados.");
            System.out.println("3 - Listar autores resgistrados.");
            System.out.println("4 - Listar autores vivos en un determiado año.");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir.");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro(sc);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos(sc);
                case 5 -> listarPorIdioma(sc);
                case 0 -> System.out.println("Hasta luego. :)");
                default -> System.out.println("Ingrese una opción válida.\n");
            }
        }
    }

    private void listarPorIdioma(Scanner sc) {
        System.out.println("\nIngrese el idioma para buscar los libros:\n" +
                "es - español\n" +
                "en - ingles\n" +
                "fr - frances\n" +
                "pt - portugues");

        String idioma = sc.nextLine();

        List<String> idiomas = List.of("es", "en", "fr", "pt");

        if (idiomas.contains(idioma)) {
            List<Libro> libros = libroRepo.findByIdioma(idioma);

            if (libros.isEmpty()) {
                System.out.println("\nNo hay libros registrados para el idioma seleccionado.");
            } else{
                for (Libro libro : libros) {
                    System.out.println("\n----------- LIBRO -----------");
                    System.out.println(libro);
                    System.out.println("-------------------------------");
                }
            }

        } else{
            System.out.println("\nIngrese un idioma valido");
            listarPorIdioma(sc);
        }
    }

    private void listarAutoresVivos(Scanner sc) {
        System.out.println("\nIngrese el año vivo de autor(es) que desea buscar");
        Integer anio = sc.nextInt();

        List<Autor> vivosConFechaMuerte = autorRepo.findByAnoNacimientoLessThanEqualAndAnoFallecimientoGreaterThanEqual(anio, anio);
        List<Autor> vivosSinFechaMuerte = autorRepo.findByAnoNacimientoLessThanEqualAndAnoFallecimientoIsNull(anio);

        List<Autor> autores = Stream.concat(vivosConFechaMuerte.stream(), vivosSinFechaMuerte.stream()).toList();

        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en ese año registrados.");
        } else {
            for(Autor autor : autores) {
                System.out.println("\n");
                System.out.println(autor.mostrarAutor());
            }
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepo.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            for(Autor autor : autores) {
                System.out.println("\n");
                System.out.println(autor.mostrarAutor());
            }
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepo.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (Libro libro : libros) {
                System.out.println("\n----------- LIBRO -----------");
                System.out.println(libro);
                System.out.println("-------------------------------");
            }
        }
    }

    private void buscarLibro(Scanner sc) {
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

*/
