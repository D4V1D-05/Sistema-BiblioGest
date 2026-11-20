package pe.edu.utp.bibliogest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.bibliogest.model.Categoria;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.service.CategoriaService;
import pe.edu.utp.bibliogest.service.LibroService;

import java.util.List;

@Controller
public class LibroController {

    private final LibroService libroService;
    private final CategoriaService categoriaService;

    public LibroController(LibroService libroService, CategoriaService categoriaService) {
        this.libroService = libroService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/inventario")
    public String listarLibros(@RequestParam(required = false) String buscar,
                               @RequestParam(required = false) String categoria,
                               Model model) {
        List<Libro> libros = libroService.listarTodos();

        if (buscar != null && !buscar.isBlank()) {
            String q = buscar.toLowerCase().trim();
            libros = libros.stream()
                    .filter(l -> l.getTitulo().toLowerCase().contains(q) || 
                                 l.getAutor().toLowerCase().contains(q) || 
                                 l.getIsbn().toLowerCase().contains(q))
                    .toList();
        } else if (categoria != null && !categoria.isBlank()) {
            libros = libros.stream()
                    .filter(l -> l.getCategoria().equalsIgnoreCase(categoria.trim()))
                    .toList();
        }

        model.addAttribute("libros", libros);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("buscar", buscar);
        model.addAttribute("categoriaActual", categoria);
        return "Libros";
    }

    @GetMapping("/libros/nuevo")
    public String formularioNuevoLibro(@RequestParam(required = false) String categoriaSeleccionada, Model model) {
        Libro libro = new Libro();
        if (categoriaSeleccionada != null && !categoriaSeleccionada.isBlank()) {
            libro.setCategoria(categoriaSeleccionada);
        }
        model.addAttribute("libro", libro);
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "NuevoLibro";
    }

    @PostMapping("/libros/guardar")
    public String guardarLibro(@ModelAttribute("libro") Libro libro) {
        libro.setStockTotal(0);
        libro.setStockDisponible(0);
        libroService.guardar(libro);
        return "redirect:/inventario";
    }

    @GetMapping("/libros/eliminar/{isbn}")
    public String eliminarLibro(@PathVariable String isbn) {
        libroService.eliminar(isbn);
        return "redirect:/inventario";
    }

    @PostMapping("/categorias/guardar-rapido")
    public String guardarCategoriaRapida(@RequestParam String nombreCategoria) {
        if (nombreCategoria != null && !nombreCategoria.isBlank()) {
            String catTrim = nombreCategoria.trim();
            categoriaService.guardar(new Categoria(null, catTrim));
            return "redirect:/libros/nuevo?categoriaSeleccionada=" + java.net.URLEncoder.encode(catTrim, java.nio.charset.StandardCharsets.UTF_8);
        }
        return "redirect:/libros/nuevo";
    }

    @GetMapping("/inventario/ingreso")
    public String vistaIngresoStock(@RequestParam(required = false) String buscarIsbn, Model model) {
        Libro libroEncontrado = null;
        boolean errorIsbn = false;

        if (buscarIsbn != null && !buscarIsbn.isBlank()) {
            libroEncontrado = libroService.buscarPorIsbn(buscarIsbn.trim());
            if (libroEncontrado == null) {
                errorIsbn = true;
            }
        } else {
            libroEncontrado = libroService.buscarPorIsbn("978-0132350884");
        }

        model.addAttribute("libroEncontrado", libroEncontrado);
        model.addAttribute("buscarIsbn", buscarIsbn);
        model.addAttribute("errorIsbn", errorIsbn);
        model.addAttribute("historialIngresos", libroService.listarIngresosRecientes());
        return "IngresoStock";
    }

    @PostMapping("/libros/actualizar")
    public String actualizarLibro(@RequestParam String isbn,
                                  @RequestParam String titulo,
                                  @RequestParam String autor,
                                  @RequestParam String editorial,
                                  @RequestParam String categoria,
                                  @RequestParam int anioPublicacion,
                                  @RequestParam String ubicacion,
                                  @RequestParam String estado) {
        libroService.actualizarLibro(isbn, titulo, autor, editorial, categoria, anioPublicacion, ubicacion, estado);
        return "redirect:/inventario";
    }

    @PostMapping("/inventario/ingreso/guardar")
    public String registrarIngresoStock(@RequestParam String isbn,
                                        @RequestParam int cantidadIngreso,
                                        @RequestParam String motivo,
                                        @RequestParam(required = false, defaultValue = "Nuevo / Excelente") String estadoMaterial) {
        libroService.abastecerStock(isbn, cantidadIngreso, motivo, estadoMaterial);
        return "redirect:/inventario/ingreso?buscarIsbn=" + isbn;
    }
}
