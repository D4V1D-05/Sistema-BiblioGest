package pe.edu.utp.bibliogest.repository;

import org.springframework.stereotype.Repository;
import pe.edu.utp.bibliogest.model.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LibroRepository {
    private final ConcurrentHashMap<String, Libro> datos = new ConcurrentHashMap<>();

    public LibroRepository() {
        guardar(new Libro("978-0132350884", "Clean Code", "Robert C. Martin", "Prentice Hall", 2024, "Ingeniería & Tecnología", 10, 6, "Estante B - Nivel 3", "Nuevo / Excelente"));
        guardar(new Libro("978-8499926223", "Sapiens: De animales a dioses", "Yuval Noah Harari", "Debate", 2014, "Ciencias Sociales & Historia", 8, 2, "Estante C - Nivel 1", "Buen Estado"));
        guardar(new Libro("978-8420412146", "Cien años de soledad", "Gabriel García Márquez", "RAE", 2012, "Literatura & Ficción", 15, 9, "Estante A - Nivel 2", "Nuevo / Excelente"));
        guardar(new Libro("978-0262033848", "Introduction to Algorithms", "Thomas H. Cormen", "MIT Press", 2022, "Ingeniería & Tecnología", 6, 1, "Estante B - Nivel 1", "Buen Estado"));
        guardar(new Libro("978-8491112345", "Anatomía Humana: Atlas Práctico", "Frank H. Netter", "Elsevier", 2023, "Medicina & Salud", 5, 4, "Estante D - Nivel 2", "Nuevo / Excelente"));
        guardar(new Libro("978-612-00-1234-5", "Tesis: Implementación de Sistemas Web en la Gestión Universitaria", "Marco Antonio Ramos (UTP)", "Tesis", 2025, 3, 3, "Estante T - Área de Investigación", "Nuevo / Excelente", "Universidad Tecnológica del Perú"));
    }

    public List<Libro> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    public Libro buscarPorIsbn(String isbn) {
        return datos.get(isbn);
    }

    public void guardar(Libro libro) {
        datos.put(libro.getIsbn(), libro);
    }

    public void eliminar(String isbn) {
        datos.remove(isbn);
    }
}
