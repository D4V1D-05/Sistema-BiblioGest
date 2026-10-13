package pe.edu.utp.bibliogest.model;

public class RegistroIngreso {
    private String fecha;
    private String isbn;
    private String titulo;
    private String motivo;
    private int cantidad;
    private String estadoMaterial;

    public RegistroIngreso() {}

    public RegistroIngreso(String fecha, String isbn, String titulo, String motivo, int cantidad) {
        this.fecha = fecha;
        this.isbn = isbn;
        this.titulo = titulo;
        this.motivo = motivo;
        this.cantidad = cantidad;
    }

    public RegistroIngreso(String fecha, String isbn, String titulo, String motivo, int cantidad, String estadoMaterial) {
        this.fecha = fecha;
        this.isbn = isbn;
        this.titulo = titulo;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.estadoMaterial = estadoMaterial;
    }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getEstadoMaterial() { return estadoMaterial; }
    public void setEstadoMaterial(String estadoMaterial) { this.estadoMaterial = estadoMaterial; }
}
