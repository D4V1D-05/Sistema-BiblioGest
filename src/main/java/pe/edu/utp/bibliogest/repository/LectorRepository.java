package pe.edu.utp.bibliogest.repository;

import org.springframework.stereotype.Repository;
import pe.edu.utp.bibliogest.model.Lector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LectorRepository {
    private final ConcurrentHashMap<String, Lector> datos = new ConcurrentHashMap<>();

    public LectorRepository() {
        guardar(new Lector("72849103", "María García López", "mgarcia@correo.pe", "987654321", 1, "Habilitado"));
        guardar(new Lector("45920184", "Carlos Mendoza R.", "cmendoza@correo.pe", "912345678", 3, "Límite alcanzado"));
        guardar(new Lector("44210987", "Jorge Ramos Peña", "jramos@correo.pe", "955443322", 1, "Con Mora / Sanción"));
        guardar(new Lector("70192834", "Ana Torres Silva", "atorres@correo.pe", "944332211", 0, "Habilitado"));
        guardar(new Lector("08912345", "Luis Herrera Vega", "lherrera@correo.pe", "977889900", 2, "Habilitado"));
    }

    public List<Lector> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    public Lector buscarPorDni(String dni) {
        return datos.get(dni);
    }

    public void guardar(Lector lector) {
        datos.put(lector.getDni(), lector);
    }

    public void eliminar(String dni) {
        datos.remove(dni);
    }
}
