package pe.edu.utp.bibliogest.model;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private Integer anio;
    private String categoria;
    private Integer stockTotal;
    private Integer stockDisponible;
    private String ubicacion;
    private String estadoMaterial;

    public Libro() {}

    public Libro(String isbn, String titulo, String autor, String editorial, Integer anio, 
                 String categoria, Integer stockTotal, Integer stockDisponible, String ubicacion, String estadoMaterial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.categoria = categoria;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
        this.ubicacion = ubicacion;
        this.estadoMaterial = estadoMaterial;
    }

    public Libro(String isbn, String titulo, String autor, String categoria, Integer anio, 
                 Integer stockTotal, Integer stockDisponible, String ubicacion, String estadoMaterial, String editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
        this.ubicacion = ubicacion;
        this.estadoMaterial = estadoMaterial;
        this.editorial = editorial;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getStockTotal() { return stockTotal; }
    public void setStockTotal(Integer stockTotal) { this.stockTotal = stockTotal; }
    public Integer getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(Integer stockDisponible) { this.stockDisponible = stockDisponible; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getEstadoMaterial() { return estadoMaterial; }
    public void setEstadoMaterial(String estadoMaterial) { this.estadoMaterial = estadoMaterial; }
}
