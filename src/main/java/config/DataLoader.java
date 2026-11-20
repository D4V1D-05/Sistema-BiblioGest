package pe.edu.utp.bibliogest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.utp.bibliogest.model.Lector;
import pe.edu.utp.bibliogest.model.Libro;
import pe.edu.utp.bibliogest.model.Prestamo;
import pe.edu.utp.bibliogest.repository.LectorRepository;
import pe.edu.utp.bibliogest.repository.LibroRepository;
import pe.edu.utp.bibliogest.repository.PrestamoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(LibroRepository libroRepo, LectorRepository lectorRepo, PrestamoRepository prestamoRepo) {
        return args -> {
            if (libroRepo.count() == 0) {


                libroRepo.save(new Libro("978-8420412146", "Cien años de soledad", "Gabriel García Márquez", "Literatura & Ficción", 1967, 15, 9, "Estante A - Nivel 2", "Bueno", "RAE"));
                libroRepo.save(new Libro("978-0132350884", "Clean Code", "Robert C. Martin", "Ingeniería & Tecnología", 2008, 10, 6, "Estante B - Nivel 3", "Nuevo", "Prentice Hall"));
                libroRepo.save(new Libro("978-8499926223", "Sapiens: De animales a dioses", "Yuval Noah Harari", "Ciencias Sociales & Historia", 2014, 8, 2, "Estante C - Nivel 1", "Bueno", "Debate"));


                lectorRepo.save(new Lector("72849103", "María García López", "mgarcia@correo.pe", "987654321", 1, "Habilitado"));
                lectorRepo.save(new Lector("45920184", "Carlos Mendoza R.", "cmendoza@correo.pe", "912345678", 0, "Habilitado"));


                Prestamo p1 = new Prestamo();
                p1.setId("PR-1001");
                p1.setDniLector("72849103");
                p1.setNombreLector("María García López");
                p1.setIsbnLibro("978-8420412146");
                p1.setTituloLibro("Cien años de soledad");
                p1.setFechaEmision("25/10/2026");
                p1.setFechaLimite("01/11/2026");
                p1.setModalidad("Presencial");
                p1.setEstado("A tiempo");
                p1.setCantidad(1);
                prestamoRepo.save(p1);

                System.out.println("✅ ¡Base de datos inicializada con datos de prueba!");
            }
        };
    }
}