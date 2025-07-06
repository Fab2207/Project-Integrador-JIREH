package Models;

public class Productos {

    private int id;
    private int codigo;
    private String nombre;
    private String descripcion;
    private Double precio_unitario;
    private int cantidad_producto;
    private String crear_producto;
    private String actualizar_producto;
    private int categorias_id;
    private String nombre_categoria;

    public Productos() {
    }

    public Productos(int id, int codigo, String nombre, String descripcion, Double precio_unitario, int cantidad_producto, String crear_producto, String actualizar_producto, int categorias_id, String nombre_categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
        this.cantidad_producto = cantidad_producto;
        this.crear_producto = crear_producto;
        this.actualizar_producto = actualizar_producto;
        this.categorias_id = categorias_id;
        this.nombre_categoria = nombre_categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public String getCrear_producto() {
        return crear_producto;
    }

    public void setCrear_producto(String crear_producto) {
        this.crear_producto = crear_producto;
    }

    public String getActualizar_producto() {
        return actualizar_producto;
    }

    public void setActualizar_producto(String actualizar_producto) {
        this.actualizar_producto = actualizar_producto;
    }

    public int getCategorias_id() {
        return categorias_id;
    }

    public void setCategorias_id(int categorias_id) {
        this.categorias_id = categorias_id;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

}
