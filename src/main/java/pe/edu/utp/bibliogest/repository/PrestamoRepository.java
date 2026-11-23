package pe.edu.utp.bibliogest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.utp.bibliogest.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, String> {

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado != 'Devuelto'")
    long contarPrestamosActivos();

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado = 'A tiempo'")
    long contarPrestamosATiempo();

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.estado = 'Devuelto'")
    long contarPrestamosDevueltos();
}

