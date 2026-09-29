package pe.edu.utp.bibliogest.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.bibliogest.model.Lector;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.model.Prestamo;
import pe.edu.utp.bibliogest.repository.LectorRepository;
import pe.edu.utp.bibliogest.repository.LibroRepository;
import pe.edu.utp.bibliogest.repository.PrestamoRepository;

import java.util.List;

@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepo;
    private final LibroRepository libroRepo;
    private final LectorRepository lectorRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, LibroRepository libroRepo, LectorRepository lectorRepo) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
        this.lectorRepo = lectorRepo;
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepo.listarTodos();
    }

    public Prestamo buscarPorId(String id) {
        return prestamoRepo.buscarPorId(id);
    }

    public boolean registrarPrestamo(Prestamo prestamo) {
        if (prestamo.getId() == null || prestamo.getId().isBlank()) {
            prestamo.setId("PR-" + (int)(Math.random() * 9000 + 1000));
        }
        if (prestamo.getEstado() == null || prestamo.getEstado().isBlank()) {
            prestamo.setEstado("A tiempo");
        }

        if (prestamo.getFechaEmision() != null && prestamo.getFechaEmision().matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] p = prestamo.getFechaEmision().split("-");
            prestamo.setFechaEmision(p[2] + "/" + p[1] + "/" + p[0]);
        }
        if (prestamo.getFechaLimite() != null && prestamo.getFechaLimite().matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] p = prestamo.getFechaLimite().split("-");
            prestamo.setFechaLimite(p[2] + "/" + p[1] + "/" + p[0]);
        }

        int cantidad = prestamo.getCantidad() > 0 ? prestamo.getCantidad() : 1;
        prestamo.setCantidad(cantidad);

        Libro libro = libroRepo.buscarPorIsbn(prestamo.getIsbnLibro());
        if (libro != null) {
            if (libro.getStockDisponible() < cantidad) {
                return false;
            }
            libro.setStockDisponible(libro.getStockDisponible() - cantidad);
            libroRepo.guardar(libro);
        }

        Lector lector = lectorRepo.buscarPorDni(prestamo.getDniLector());
        if (lector != null) {
            lector.setPrestamosActivos(lector.getPrestamosActivos() + cantidad);
            if (lector.getPrestamosActivos() >= 3) {
                lector.setEstadoCuenta("Límite alcanzado");
            }
            lectorRepo.guardar(lector);
        }

        prestamoRepo.guardar(prestamo);
        return true;
    }

    public boolean procesarDevolucion(String prestamoId, String fechaDevolucion, String observaciones) {
        Prestamo prestamo = prestamoRepo.buscarPorId(prestamoId);
        if (prestamo == null) {
            return false;
        }

        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setObservaciones(observaciones);
        prestamo.setEstado("Devuelto");
        prestamoRepo.guardar(prestamo);

        int cantidad = prestamo.getCantidad() > 0 ? prestamo.getCantidad() : 1;

        Libro libro = libroRepo.buscarPorIsbn(prestamo.getIsbnLibro());
        if (libro != null) {
            libro.setStockDisponible(libro.getStockDisponible() + cantidad);
            libroRepo.guardar(libro);
        }

        Lector lector = lectorRepo.buscarPorDni(prestamo.getDniLector());
        if (lector != null && lector.getPrestamosActivos() > 0) {
            lector.setPrestamosActivos(Math.max(0, lector.getPrestamosActivos() - cantidad));
            if (!lector.getEstadoCuenta().equals("Con Mora / Sanción")) {
                lector.setEstadoCuenta("Habilitado");
            }
            lectorRepo.guardar(lector);
        }

        return true;
    }

    public Prestamo buscarPorCodigoODni(String query) {
        if (query == null || query.isBlank()) return null;
        String q = query.trim().toUpperCase();
        return prestamoRepo.listarTodos().stream()
                .filter(p -> !"Devuelto".equalsIgnoreCase(p.getEstado()))
                .filter(p -> (p.getId() != null && p.getId().toUpperCase().contains(q)) || 
                             (p.getIdPrestamo() != null && p.getIdPrestamo().toUpperCase().contains(q)) ||
                             (p.getDniLector() != null && p.getDniLector().contains(q)))
                .findFirst()
                .orElse(null);
    }

    public boolean devolverEjemplar(String idPrestamo, String condicion, String penalizacion, String observaciones) {
        Prestamo prestamo = prestamoRepo.listarTodos().stream()
                .filter(p -> (p.getId() != null && p.getId().equalsIgnoreCase(idPrestamo)) ||
                             (p.getIdPrestamo() != null && p.getIdPrestamo().equalsIgnoreCase(idPrestamo)))
                .findFirst()
                .orElse(null);

        if (prestamo != null && !"Devuelto".equalsIgnoreCase(prestamo.getEstado())) {
            prestamo.setEstado("Devuelto");
            if (observaciones != null && !observaciones.isBlank()) {
                prestamo.setObservaciones(observaciones);
            }
            prestamoRepo.guardar(prestamo);

            if (prestamo.getIsbnLibro() != null) {
                Libro libro = libroRepo.buscarPorIsbn(prestamo.getIsbnLibro());
                if (libro != null) {
                    int cantidadRetorno = prestamo.getCantidad() > 0 ? prestamo.getCantidad() : 1;
                    libro.setStockDisponible(libro.getStockDisponible() + cantidadRetorno);
                    libroRepo.guardar(libro);
                }
            }

            if (penalizacion != null && !penalizacion.contains("Sin penalización")) {
                Lector lector = lectorRepo.buscarPorDni(prestamo.getDniLector());
                if (lector != null) {
                    lector.setEstadoCuenta("Con Mora / Sanción");
                    lectorRepo.guardar(lector);
                }
            } else {
                Lector lector = lectorRepo.buscarPorDni(prestamo.getDniLector());
                if (lector != null && lector.getPrestamosActivos() > 0) {
                    int cantidadRetorno = prestamo.getCantidad() > 0 ? prestamo.getCantidad() : 1;
                    lector.setPrestamosActivos(Math.max(0, lector.getPrestamosActivos() - cantidadRetorno));
                    if (!"Con Mora / Sanción".equals(lector.getEstadoCuenta())) {
                        lector.setEstadoCuenta("Habilitado");
                    }
                    lectorRepo.guardar(lector);
                }
            }
            return true;
        }
        return false;
    }
}
