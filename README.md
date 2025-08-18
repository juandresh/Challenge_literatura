# 📚 LiteraturaApp

Aplicación en **Java Spring Boot** para consultar, registrar y listar libros y autores.  
Permite buscar libros por título usando la API de [Gutendex](https://gutendex.com/), almacenarlos en una base de datos y realizar diferentes consultas, todo desde la **consola**.

---

## 🚀 Funcionalidades

- 🔍 **Buscar libro por título** (consume la API de Gutendex y lo guarda en la base de datos).
- 📖 **Listar libros registrados**.
- ✍️ **Listar autores registrados**.
- 🧓 **Listar autores vivos en un año específico**.
- 🌍 **Listar libros por idioma** (es, en, fr, pt).

---

## 🛠️ Tecnologías utilizadas

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **PostgreSQL**
- **Jackson** para manejo de JSON
- **HTTP Client** para consumo de API externa
- **Maven** como gestor de dependencias

---

## 📦 Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [Java 17 o superior](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

---

## 📥 Instalación y ejecución

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/juandresh/Challenge_literatura.git
   cd literatura-app
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```
   O bien:
   ```bash
   java -jar target/literatura-0.0.1-SNAPSHOT.jar
   ```

4. **Interacción por consola**  
   Una vez iniciada, aparecerá el menú:
   ```
   1 - Buscar libro por título
   2 - Listar libros registrados
   3 - Listar autores registrados
   4 - Listar autores vivos en un determinado año
   5 - Listar libros por idioma
   0 - Salir
   ```

---

## ⚙️ Configuración de la base de datos

Por defecto, el proyecto está configurado para usar **PostgreSQL** en memoria.  

Si quieres usar MySQL u otra base de datos, modifica `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/literatura
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🌐 API utilizada

Se consume la API pública de Gutendex:

```
https://gutendex.com/books/?search=<titulo>
```

Ejemplo de búsqueda:
```
https://gutendex.com/books/?search=Don%20Quijote
```

---
