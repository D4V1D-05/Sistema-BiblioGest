package pe.edu.utp.bibliogest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.bibliogest.model.Lector;
import pe.edu.utp.bibliogest.service.LectorService;

@Controller
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping("/lectores")
    public String listarLectores(Model model) {
        model.addAttribute("lectores", lectorService.listarTodos());
        model.addAttribute("nuevoLector", new Lector());
        return "Lectores";
    }

    @PostMapping("/lectores/guardar")
    public String guardarLector(@ModelAttribute("nuevoLector") Lector lector) {
        lectorService.guardar(lector);
        return "redirect:/lectores";
    }

    @GetMapping("/lectores/eliminar/{dni}")
    public String eliminarLector(@PathVariable String dni) {
        lectorService.eliminar(dni);
        return "redirect:/lectores";
    }

    @PostMapping("/lectores/actualizar")
    public String actualizarLector(@RequestParam String dni,
                                   @RequestParam String nombresApellidos,
                                   @RequestParam String correo,
                                   @RequestParam(required = false) String telefono,
                                   @RequestParam String estadoCuenta) {
        lectorService.actualizarLector(dni, nombresApellidos, correo, telefono, estadoCuenta);
        return "redirect:/lectores";
    }
}
