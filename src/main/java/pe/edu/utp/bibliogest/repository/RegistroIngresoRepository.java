package pe.edu.utp.bibliogest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.bibliogest.model.RegistroIngreso;

public interface RegistroIngresoRepository extends JpaRepository<RegistroIngreso, Long> {
}