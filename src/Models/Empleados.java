package Models;

public class Empleados {

    private int id;
    private String nombre_completo;
    private String usuario;
    private String direccion;
    private String celular;
    private String correo_electronico;
    private String contraseña;
    private String rol;
    private String fecha_registro;
    private String actualizar_informacion;

    public Empleados() {
    }

    public Empleados(int id, String nombre_completo, String usuario, String direccion, String celular, String correo_electronico, String contraseña, String rol, String fecha_registro, String actualizar_informacion) {
        this.id = id;
        this.nombre_completo = nombre_completo;
        this.usuario = usuario;
        this.direccion = direccion;
        this.celular = celular;
        this.correo_electronico = correo_electronico;
        this.contraseña = contraseña;
        this.rol = rol;
        this.fecha_registro = fecha_registro;
        this.actualizar_informacion = actualizar_informacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
