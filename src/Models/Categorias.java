package Models;

public class Categorias {

    private int id;
    private String nombre;
    private String fecha_registro;
    private String actualizar_categoria;

    public Categorias() {
    }

    public Categorias(int id, String nombre, String fecha_registro, String actualizar_categoria) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_registro = fecha_registro;
        this.actualizar_categoria = actualizar_categoria;
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

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getActualizar_categoria() {
        return actualizar_categoria;
    }

    public void setActualizar_categoria(String actualizar_categoria) {
        this.actualizar_categoria = actualizar_categoria;
    }

}
