package pe.edu.utp.bibliogest.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.bibliogest.model.Lector;
import pe.edu.utp.bibliogest.repository.LectorRepository;

import java.util.List;

@Service
public class LectorService {
    private final LectorRepository repository;

    public LectorService(LectorRepository repository) {
        this.repository = repository;
    }

    public List<Lector> listarTodos() {

        return repository.findAll();
    }

    public Lector buscarPorDni(String dni) {
        Lector lector = repository.findByDni(dni).orElse(null);

        if (lector != null && lector.getFechaFinSancion() != null) {
            java.time.LocalDate fechaFin = java.time.LocalDate.parse(lector.getFechaFinSancion());
            java.time.LocalDate hoy = java.time.LocalDate.now();

            if (hoy.isAfter(fechaFin) || hoy.isEqual(fechaFin)) {
                lector.setEstadoCuenta("Habilitado");
                lector.setFechaFinSancion(null);
                repository.save(lector);
            }
        }

        return lector;
    }

    public void guardar(Lector lector) {
        if (lector.getPrestamosActivos() == null) {
            lector.setPrestamosActivos(0);
        }
        if (lector.getEstadoCuenta() == null || lector.getEstadoCuenta().isBlank()) {
            lector.setEstadoCuenta("Habilitado");
        }

        repository.save(lector);
    }

    public void eliminar(String dni) {

        repository.deleteById(dni);
    }

    public void actualizarLector(String dni, String nombresApellidos, String correo, String telefono, String estadoCuenta) {
        Lector lector = buscarPorDni(dni);
        if (lector != null) {
            lector.setNombresApellidos(nombresApellidos);
            lector.setCorreo(correo);
            lector.setTelefono(telefono);
            if (estadoCuenta != null && !estadoCuenta.isBlank()) {
                lector.setEstadoCuenta(estadoCuenta);
            }

            repository.save(lector);

        }
    }

    public void actualizarLector(String dni, String nombresApellidos, String correo, String telefono) {
        actualizarLector(dni, nombresApellidos, correo, telefono, null);
    }

    public void levantarSancion(String dni) {

        Lector lector = buscarPorDni(dni);
        if (lector != null) {
            lector.setEstadoCuenta("Habilitado");

            repository.save(lector);
        }
    }
}

