package Models;

public class Clientes {

    private int id;
    private String nombre_completo;
    private String direccion;
    private String celular;
    private String correo_electronico;
    private String crear_cliente;
    private String actualizar_cliente;

    public Clientes() {
    }

    public Clientes(int id, String nombre_completo, String direccion, String celular, String correo_electronico, String crear_cliente, String actualizar_cliente) {
        this.id = id;
        this.nombre_completo = nombre_completo;
        this.direccion = direccion;
        this.celular = celular;
        this.correo_electronico = correo_electronico;
        this.crear_cliente = crear_cliente;
        this.actualizar_cliente = actualizar_cliente;
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

    public String getCrear_cliente() {
        return crear_cliente;
    }

    public void setCrear_cliente(String crear_cliente) {
        this.crear_cliente = crear_cliente;
    }

    public String getActualizar_cliente() {
        return actualizar_cliente;
    }

    public void setActualizar_cliente(String actualizar_cliente) {
        this.actualizar_cliente = actualizar_cliente;
    }

}
