package pe.edu.utp.bibliogest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.bibliogest.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, String> {

}

