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
        return repository.listarTodos();
    }

    public Lector buscarPorDni(String dni) {
        return repository.buscarPorDni(dni);
    }

    public void guardar(Lector lector) {
        if (lector.getPrestamosActivos() == null) {
            lector.setPrestamosActivos(0);
        }
        if (lector.getEstadoCuenta() == null || lector.getEstadoCuenta().isBlank()) {
            lector.setEstadoCuenta("Habilitado");
        }
        repository.guardar(lector);
    }

    public void eliminar(String dni) {
        repository.eliminar(dni);
    }

    public void actualizarLector(String dni, String nombresApellidos, String correo, String telefono, String estadoCuenta) {
        Lector lector = repository.buscarPorDni(dni);
        if (lector != null) {
            lector.setNombresApellidos(nombresApellidos);
            lector.setCorreo(correo);
            lector.setTelefono(telefono);
            if (estadoCuenta != null && !estadoCuenta.isBlank()) {
                lector.setEstadoCuenta(estadoCuenta);
            }
            repository.guardar(lector);
        }
    }

    public void actualizarLector(String dni, String nombresApellidos, String correo, String telefono) {
        actualizarLector(dni, nombresApellidos, correo, telefono, null);
    }

    public void levantarSancion(String dni) {
        Lector lector = repository.buscarPorDni(dni);
        if (lector != null) {
            lector.setEstadoCuenta("Habilitado");
            repository.guardar(lector);
        }
    }
}
