package pe.edu.utp.bibliogest.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.model.RegistroIngreso;
import pe.edu.utp.bibliogest.repository.LibroRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LibroService {
    private final LibroRepository repository;
    private final List<RegistroIngreso> historialIngresos = new CopyOnWriteArrayList<>();

    {
        historialIngresos.add(new RegistroIngreso("26/09/2026", "978-0132350884", "Clean Code: Manual de desarrollo ágil", "Compra institucional", 5, "Nuevo / Excelente"));
        historialIngresos.add(new RegistroIngreso("24/09/2026", "978-8420412146", "Cien años de soledad", "Donación", 3, "Buen Estado"));
        historialIngresos.add(new RegistroIngreso("20/09/2026", "978-0262033848", "Introduction to Algorithms", "Compra institucional", 2, "Buen Estado"));
    }

    public LibroService(LibroRepository repository) {
        this.repository = repository;
    }

    public List<Libro> listarTodos() {
        return repository.listarTodos();
    }

    public Libro buscarPorIsbn(String isbn) {
        return repository.buscarPorIsbn(isbn);
    }

    public void guardar(Libro libro) {
        if (libro.getStockTotal() == null) {
            libro.setStockTotal(0);
        }
        if (libro.getStockDisponible() == null) {
            libro.setStockDisponible(libro.getStockTotal());
        }
        repository.guardar(libro);
    }

    public void eliminar(String isbn) {
        repository.eliminar(isbn);
    }

    public List<RegistroIngreso> listarIngresosRecientes() {
        return historialIngresos;
    }

    public boolean abastecerStock(String isbn, int cantidadIngreso, String motivo, String estadoMaterial) {
        Libro libro = repository.buscarPorIsbn(isbn);
        if (libro != null && cantidadIngreso > 0) {
            libro.setStockTotal(libro.getStockTotal() + cantidadIngreso);
            libro.setStockDisponible(libro.getStockDisponible() + cantidadIngreso);
            if (estadoMaterial != null && !estadoMaterial.isBlank()) {
                libro.setEstadoMaterial(estadoMaterial);
            }
            repository.guardar(libro);

            String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            RegistroIngreso registro = new RegistroIngreso(fechaActual, isbn, libro.getTitulo(), motivo, cantidadIngreso);
            registro.setEstadoMaterial(estadoMaterial);
            historialIngresos.add(0, registro);
            return true;
        }
        return false;
    }

    public boolean abastecerStock(String isbn, int cantidadIngreso, String motivo, String ubicacion, String estadoMaterial) {
        return abastecerStock(isbn, cantidadIngreso, motivo, estadoMaterial);
    }

    public boolean abastecerStock(String isbn, int cantidadIngreso) {
        return abastecerStock(isbn, cantidadIngreso, "Compra institucional", "Nuevo / Excelente");
    }

    public void actualizarLibro(String isbn, String titulo, String autor, String editorial, 
                                 String categoria, int anioPublicacion, String ubicacion, String estado) {
        Libro libro = repository.buscarPorIsbn(isbn);
        if (libro != null) {
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setCategoria(categoria);
            libro.setAnioPublicacion(anioPublicacion);
            libro.setUbicacion(ubicacion);
            if (estado != null && !estado.isBlank()) {
                libro.setEstado(estado);
            }
            repository.guardar(libro);
        }
    }
}
