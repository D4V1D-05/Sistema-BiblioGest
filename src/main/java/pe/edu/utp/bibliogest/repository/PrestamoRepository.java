package pe.edu.utp.bibliogest.repository;

import org.springframework.stereotype.Repository;
import pe.edu.utp.bibliogest.model.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PrestamoRepository {
    private final ConcurrentHashMap<String, Prestamo> datos = new ConcurrentHashMap<>();

    public PrestamoRepository() {
        guardar(new Prestamo("PR-8492", "72849103", "María García López", "978-0132350884", "Clean Code", "28/08/2026", "15/09/2026", "Préstamo a Domicilio", "A tiempo"));
        guardar(new Prestamo("PR-8480", "44210987", "Jorge Ramos Peña", "978-0262033848", "Introduction to Algorithms", "20/08/2026", "03/09/2026", "Préstamo a Domicilio", "Por vencer"));
        guardar(new Prestamo("PR-8310", "45920184", "Carlos Mendoza R.", "978-8499926223", "Sapiens: De animales a dioses", "10/08/2026", "25/08/2026", "Préstamo a Domicilio", "Vencido"));
        guardar(new Prestamo("PR-8495", "70192834", "Ana Torres Silva", "978-8420412146", "Cien años de soledad", "30/08/2026", "20/09/2026", "Lectura en Sala", "A tiempo"));
    }

    public List<Prestamo> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    public Prestamo buscarPorId(String id) {
        return datos.get(id);
    }

    public void guardar(Prestamo prestamo) {
        datos.put(prestamo.getId(), prestamo);
    }
}
