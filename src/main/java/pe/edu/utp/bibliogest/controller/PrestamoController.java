package pe.edu.utp.bibliogest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.bibliogest.model.Prestamo;
import pe.edu.utp.bibliogest.service.LectorService;
import pe.edu.utp.bibliogest.service.LibroService;
import pe.edu.utp.bibliogest.service.PrestamoService;

@Controller
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final LibroService libroService;
    private final LectorService lectorService;

    public PrestamoController(PrestamoService prestamoService, LibroService libroService, LectorService lectorService) {
        this.prestamoService = prestamoService;
        this.libroService = libroService;
        this.lectorService = lectorService;
    }

    @GetMapping("/prestamos/registrar")
    public String vistaRegistrarPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", libroService.listarTodos());
        model.addAttribute("lectores", lectorService.listarTodos());
        return "RealizarPrestamos";
    }

    @PostMapping("/prestamos/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo) {
        prestamoService.registrarPrestamo(prestamo);
        return "redirect:/prestamos/estado";
    }

    @GetMapping("/prestamos/devolucion")
    public String vistaDevolucion(@RequestParam(required = false) String codigoPrestamo, Model model) {
        Prestamo prestamoEncontrado = null;
        boolean errorBusqueda = false;

        if (codigoPrestamo != null && !codigoPrestamo.isBlank()) {
            prestamoEncontrado = prestamoService.buscarPorCodigoODni(codigoPrestamo.trim());
            if (prestamoEncontrado == null) {
                errorBusqueda = true;
            }
        } else {
            prestamoEncontrado = prestamoService.listarTodos().stream()
                    .filter(p -> !"Devuelto".equalsIgnoreCase(p.getEstado()))
                    .findFirst()
                    .orElse(null);
        }

        model.addAttribute("prestamoEncontrado", prestamoEncontrado);
        model.addAttribute("codigoPrestamo", codigoPrestamo);
        model.addAttribute("errorBusqueda", errorBusqueda);
        model.addAttribute("prestamos", prestamoService.listarTodos());
        model.addAttribute("fechaHoy", java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return "MarcarDevolucion";
    }

    @PostMapping("/prestamos/devolucion/confirmar")
    public String confirmarDevolucion(@RequestParam String idPrestamo,
                                      @RequestParam(required = false) String condicion,
                                      @RequestParam(required = false) String penalizacion,
                                      @RequestParam(required = false) String observaciones) {
        prestamoService.devolverEjemplar(idPrestamo, condicion, penalizacion, observaciones);
        return "redirect:/prestamos/estado";
    }

    @PostMapping("/prestamos/devolver")
    public String procesarDevolucion(@RequestParam String prestamoId,
                                     @RequestParam(required = false) String fechaDevolucion,
                                     @RequestParam(required = false) String observaciones) {
        prestamoService.devolverEjemplar(prestamoId, null, null, observaciones);
        return "redirect:/prestamos/estado";
    }

    @GetMapping("/prestamos/estado")
    public String vistaEstadoPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listarTodos());
        return "Estado";
    }
}
