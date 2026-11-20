package pe.edu.utp.bibliogest.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.model.RegistroIngreso;
import pe.edu.utp.bibliogest.repository.LibroRepository;
import pe.edu.utp.bibliogest.repository.RegistroIngresoRepository;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final RegistroIngresoRepository registroIngresoRepository;

    public LibroService(LibroRepository libroRepository, RegistroIngresoRepository registroIngresoRepository) {
        this.libroRepository = libroRepository;
        this.registroIngresoRepository = registroIngresoRepository;
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public void guardar(Libro libro) {
        libroRepository.save(libro);
    }

    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn).orElse(null);
    }

    public void eliminar(String isbn) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro != null) {
            libroRepository.delete(libro);
        }
    }

    public void actualizarLibro(String isbn, String titulo, String autor, String editorial,
                                String categoria, int anioPublicacion, String ubicacion, String estado) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro != null) {
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setCategoria(categoria);
            libro.setAnioPublicacion(anioPublicacion);
            libro.setUbicacion(ubicacion);
            libro.setEstado(estado);
            libroRepository.save(libro);
        }
    }


    public List<RegistroIngreso> listarIngresosRecientes() {
        return registroIngresoRepository.findAll();
    }


    public void abastecerStock(String isbn, int cantidadIngreso, String motivo, String estadoMaterial) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro != null) {
            libro.setStockTotal(libro.getStockTotal() + cantidadIngreso);
            libro.setStockDisponible(libro.getStockDisponible() + cantidadIngreso);
            libroRepository.save(libro);


            RegistroIngreso ingreso = new RegistroIngreso();
            ingreso.setIsbn(isbn);
            ingreso.setTitulo(libro.getTitulo());
            ingreso.setCantidad(cantidadIngreso);
            ingreso.setMotivo(motivo);
            ingreso.setEstadoMaterial(estadoMaterial);
            ingreso.setFecha(java.time.LocalDate.now().toString());

            registroIngresoRepository.save(ingreso);
        }
    }
}