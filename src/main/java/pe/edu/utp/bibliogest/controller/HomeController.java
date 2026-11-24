package pe.edu.utp.bibliogest.controller;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.bibliogest.model.Prestamo;
import pe.edu.utp.bibliogest.model.Libro;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.utp.bibliogest.repository.LectorRepository;
import pe.edu.utp.bibliogest.repository.LibroRepository;
import pe.edu.utp.bibliogest.repository.PrestamoRepository;

import java.util.List;

@Controller
public class HomeController {

    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;
    private final PrestamoRepository prestamoRepository;

    public HomeController(LibroRepository libroRepository, LectorRepository lectorRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalLibros", libroRepository.count());
        model.addAttribute("totalLectores", lectorRepository.count());
        model.addAttribute("prestamosActivos", prestamoRepository.contarPrestamosActivos());
        model.addAttribute("prestamosDevueltos", prestamoRepository.contarPrestamosDevueltos());
        return "index";
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/recuperar")
    public String recuperar() { return "Formulario"; }

    @GetMapping("/novedades")
    public String novedades() { return "Novedades"; }

    @GetMapping("/contacto")
    public String contacto() { return "Contacto"; }

    @GetMapping("/reportes")
    public String reportes(@RequestParam(required = false) String anio,
                           @RequestParam(required = false) String mes,
                           @RequestParam(required = false) String categoria,
                           Model model) {
        List<Prestamo> todosLosPrestamos = prestamoRepository.findAll();
        List<Prestamo> prestamosFiltrados = todosLosPrestamos.stream()
                .filter(p -> {
                    boolean cumpleAnio = true;
                    boolean cumpleMes = true;
                    boolean cumpleCat = true;

                    if (anio != null && !anio.equals("todos")) {
                        cumpleAnio = p.getFechaEmision() != null && p.getFechaEmision().contains(anio);
                    }
                    if (mes != null && !mes.equals("todos")) {
                        cumpleMes = p.getFechaEmision() != null && p.getFechaEmision().contains("/" + mes + "/");
                    }
                    if (categoria != null && !categoria.equals("todas")) {
                        Libro libro = libroRepository.findByIsbn(p.getIsbnLibro()).orElse(null);
                        cumpleCat = libro != null && categoria.equalsIgnoreCase(libro.getCategoria());
                    }

                    return cumpleAnio && cumpleMes && cumpleCat;
                })
                .toList();

        long totalPrestamos = prestamosFiltrados.size();
        long activos = prestamosFiltrados.stream().filter(p -> !"Devuelto".equalsIgnoreCase(p.getEstado())).count();
        long devueltos = prestamosFiltrados.stream().filter(p -> "Devuelto".equalsIgnoreCase(p.getEstado())).count();
        double tasaDevolucion = totalPrestamos > 0 ? ((double) devueltos / totalPrestamos) * 100 : 0;

        model.addAttribute("totalPrestamos", totalPrestamos);
        model.addAttribute("prestamosDevueltos", devueltos);
        model.addAttribute("prestamosActivos", activos);
        model.addAttribute("totalLectores", lectorRepository.count());
        model.addAttribute("tasaDevolucion", String.format("%.1f", tasaDevolucion));

        model.addAttribute("anio", anio);
        model.addAttribute("mes", mes);
        model.addAttribute("categoria", categoria);

        model.addAttribute("prestamosPorCategoria", prestamoRepository.contarPrestamosPorCategoria());

        return "Reportes";
    }
}
