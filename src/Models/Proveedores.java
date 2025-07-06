package Models;

public class Proveedores {

    private int id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String celular;
    private String email;
    private String distrito;
    private String fecha_registro;
    private String actualizar_informacion;

    public Proveedores() {
    }

    public Proveedores(int id, String nombre, String descripcion, String direccion, String celular, String email, String distrito, String fecha_registro, String actualizar_informacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.celular = celular;
        this.email = email;
        this.distrito = distrito;
        this.fecha_registro = fecha_registro;
        this.actualizar_informacion = actualizar_informacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getActualizar_informacion() {
        return actualizar_informacion;
    }

    public void setActualizar_informacion(String actualizar_informacion) {
        this.actualizar_informacion = actualizar_informacion;
    }

}
