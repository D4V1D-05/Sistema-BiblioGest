package pe.edu.utp.bibliogest.repository;

import org.springframework.stereotype.Repository;
import pe.edu.utp.bibliogest.model.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CategoriaRepository {
    private final ConcurrentHashMap<Long, Categoria> datos = new ConcurrentHashMap<>();

    public CategoriaRepository() {
        guardar(new Categoria(1L, "Ingeniería & Tecnología"));
        guardar(new Categoria(2L, "Ciencias Sociales & Historia"));
        guardar(new Categoria(3L, "Literatura & Ficción"));
        guardar(new Categoria(4L, "Medicina & Salud"));
        guardar(new Categoria(5L, "Tesis"));
    }

    public List<Categoria> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    public void guardar(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId((long) (datos.size() + 1));
        }
        datos.put(categoria.getId(), categoria);
    }
}
