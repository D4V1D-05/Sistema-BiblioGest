package pe.edu.utp.bibliogest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.bibliogest.model.Libro;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByIsbn(String isbn);
}

