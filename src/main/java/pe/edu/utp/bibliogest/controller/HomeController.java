package pe.edu.utp.bibliogest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.utp.bibliogest.repository.LectorRepository;
import pe.edu.utp.bibliogest.repository.LibroRepository;
import pe.edu.utp.bibliogest.repository.PrestamoRepository;

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
    public String reportes() { return "Reportes"; }
}