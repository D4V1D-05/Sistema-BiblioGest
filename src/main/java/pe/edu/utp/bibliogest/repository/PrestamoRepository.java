package pe.edu.utp.bibliogest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.utp.bibliogest.model.Prestamo;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, String> {

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado != 'Devuelto'")
    long contarPrestamosActivos();

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado = 'A tiempo'")
    long contarPrestamosATiempo();

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado = 'Devuelto'")
    long contarPrestamosDevueltos();

    // Consulta para agrupar los préstamos por la categoría del libro
    @Query("SELECT l.categoria, COUNT(p) FROM Prestamo p JOIN Libro l ON p.isbnLibro = l.isbn GROUP BY l.categoria ORDER BY COUNT(p) DESC")
    List<Object[]> contarPrestamosPorCategoria();
}

