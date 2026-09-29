package pe.edu.utp.bibliogest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/recuperar")
    public String recuperar() {
        return "Formulario";
    }

    @GetMapping("/novedades")
    public String novedades() {
        return "Novedades";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "Contacto";
    }

    @GetMapping("/reportes")
    public String reportes() {
        return "Reportes";
    }
}
