package pe.edu.utp.bibliogest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.bibliogest.model.Lector;
import java.util.Optional;

public interface LectorRepository extends JpaRepository<Lector, String> {

    Optional<Lector> findByDni(String dni);
}