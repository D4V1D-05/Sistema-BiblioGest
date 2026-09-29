package pe.edu.utp.bibliogest.model;

public class Prestamo {
    private String id;
    private String dniLector;
    private String nombreLector;
    private String isbnLibro;
    private String tituloLibro;
    private String fechaEmision;
    private String fechaLimite;
    private String fechaDevolucion;
    private String modalidad;
    private String estado;
    private String observaciones;
    private int cantidad = 1;

    public Prestamo() {}

    public Prestamo(String id, String dniLector, String nombreLector, String isbnLibro, String tituloLibro, 
                    String fechaEmision, String fechaLimite, String modalidad, String estado) {
        this.id = id;
        this.dniLector = dniLector;
        this.nombreLector = nombreLector;
        this.isbnLibro = isbnLibro;
        this.tituloLibro = tituloLibro;
        this.fechaEmision = fechaEmision;
        this.fechaLimite = fechaLimite;
        this.modalidad = modalidad;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdPrestamo() { return id; }
    public void setIdPrestamo(String idPrestamo) { this.id = idPrestamo; }
    public String getDniLector() { return dniLector; }
    public void setDniLector(String dniLector) { this.dniLector = dniLector; }
    public String getNombreLector() { return nombreLector; }
    public void setNombreLector(String nombreLector) { this.nombreLector = nombreLector; }
    public String getIsbnLibro() { return isbnLibro; }
    public void setIsbnLibro(String isbnLibro) { this.isbnLibro = isbnLibro; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }
    public String getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(String fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
