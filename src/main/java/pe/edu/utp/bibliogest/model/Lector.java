package pe.edu.utp.bibliogest.model;

public class Lector {
    private String dni;
    private String nombre;
    private String correo;
    private String telefono;
    private Integer prestamosActivos;
    private String estado;

    public Lector() {}

    public Lector(String dni, String nombre, String correo, String telefono, Integer prestamosActivos, String estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.prestamosActivos = prestamosActivos;
        this.estado = estado;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombreCompleto() { return nombre; }
    public void setNombreCompleto(String nombreCompleto) { this.nombre = nombreCompleto; }
    public String getNombresApellidos() { return nombre; }
    public void setNombresApellidos(String nombresApellidos) { this.nombre = nombresApellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCorreoElectronico() { return correo; }
    public void setCorreoElectronico(String correoElectronico) { this.correo = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Integer getPrestamosActivos() { return prestamosActivos; }
    public void setPrestamosActivos(Integer prestamosActivos) { this.prestamosActivos = prestamosActivos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEstadoCuenta() { return estado; }
    public void setEstadoCuenta(String estadoCuenta) { this.estado = estadoCuenta; }
}
