package pe.edu.utp.bibliogest.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroRestController {

    private final LibroService libroService;

    public LibroRestController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> listarTodos() {
        return libroService.listarTodos();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> buscarPorIsbn(@PathVariable String isbn) {
        Libro libro = libroService.buscarPorIsbn(isbn);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        libro.setStockTotal(0); // Mantenemos tu lógica
        libro.setStockDisponible(0);
        libroService.guardar(libro);
        return ResponseEntity.ok(libro);
    }
}