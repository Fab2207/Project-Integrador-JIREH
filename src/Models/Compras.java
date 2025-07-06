package Models;

public class Compras {

    private int id;
    private int codigo;
    private String nombre_producto;
    private int compras_cantidad;
    private double compras_precio;
    private double compras_subtotal;
    private double total;
    private String crear_compra;
    private String nombre_proveedor_producto;
    private String compra;

    public Compras() {
    }

    public Compras(int id, int codigo, String nombre_producto, int compras_cantidad, double compras_precio, double compras_subtotal, double total, String crear_compra, String nombre_proveedor_producto, String compra) {
        this.id = id;
        this.codigo = codigo;
        this.nombre_producto = nombre_producto;
        this.compras_cantidad = compras_cantidad;
        this.compras_precio = compras_precio;
        this.compras_subtotal = compras_subtotal;
        this.total = total;
        this.crear_compra = crear_compra;
        this.nombre_proveedor_producto = nombre_proveedor_producto;
        this.compra = compra;
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

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getCompras_cantidad() {
        return compras_cantidad;
    }

    public void setCompras_cantidad(int compras_cantidad) {
        this.compras_cantidad = compras_cantidad;
    }

    public double getCompras_precio() {
        return compras_precio;
    }

    public void setCompras_precio(double compras_precio) {
        this.compras_precio = compras_precio;
    }

    public double getCompras_subtotal() {
        return compras_subtotal;
    }

    public void setCompras_subtotal(double compras_subtotal) {
        this.compras_subtotal = compras_subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCrear_compra() {
        return crear_compra;
    }

    public void setCrear_compra(String crear_compra) {
        this.crear_compra = crear_compra;
    }

    public String getNombre_proveedor_producto() {
        return nombre_proveedor_producto;
    }

    public void setNombre_proveedor_producto(String nombre_proveedor_producto) {
        this.nombre_proveedor_producto = nombre_proveedor_producto;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

}
