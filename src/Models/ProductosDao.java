package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductosDao {

    // Instancia conexión
    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar producto
    public boolean registrarProductoQuery(Productos product) {
        String query = "INSERT INTO productos(codigo, nombre, descripcion, "
                + "precio_unitario, crear_producto, actualizar_producto, categorias_id) "
                + "VALUES(?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, product.getCodigo());
            pst.setString(2, product.getNombre());
            pst.setString(3, product.getDescripcion());
            pst.setDouble(4, product.getPrecio_unitario());
            pst.setTimestamp(5, dateTime);
            pst.setTimestamp(6, dateTime);
            pst.setInt(7, product.getCategorias_id());

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + e);
            return false;
        }
    }

    // Listar productos
    public List listarProductoQuery(String value) {
        List<Productos> list_productos = new ArrayList();
        String query = "SELECT pro.*, ca.nombre AS nombre_categoria FROM productos pro "
                + "INNER JOIN categorias ca ON pro.categorias_id = ca.id";
        String query_buscar_producto = "SELECT pro.*, ca.nombre AS nombre_categoria "
                + "FROM productos pro INNER JOIN categorias ca ON pro.categorias_id = ca.id "
                + "WHERE pro.nombre LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(value.equalsIgnoreCase("") ? query : query_buscar_producto);
            rs = pst.executeQuery();

            while (rs.next()) {
                Productos product = new Productos();
                product.setId(rs.getInt("id"));
                product.setCodigo(rs.getInt("codigo"));
                product.setNombre(rs.getString("nombre"));
                product.setDescripcion(rs.getString("descripcion"));
                product.setPrecio_unitario(rs.getDouble("precio_unitario"));
                product.setCantidad_producto(rs.getInt("cantidad_producto"));
                product.setNombre_categoria(rs.getString("nombre_categoria"));
                list_productos.add(product);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e);
        }
        return list_productos;
    }

    // Actualizar producto
    public boolean actualizarProductoQuery(Productos product) {
        String query = "UPDATE productos SET codigo=?, nombre=?, descripcion=?, "
                + "precio_unitario=?, actualizar_producto=?, categorias_id=? WHERE id=?";

        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, product.getCodigo());
            pst.setString(2, product.getNombre());
            pst.setString(3, product.getDescripcion());
            pst.setDouble(4, product.getPrecio_unitario());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, product.getCategorias_id());
            pst.setInt(7, product.getId());

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el producto: " + e);
            return false;
        }
    }

    // Eliminar producto con reordenamiento de IDs
    public boolean eliminarProductoQuery(int id) {
        String query = "DELETE FROM productos WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.executeUpdate();

            reordenarIdsProductos(); // ← reorganiza los IDs tras eliminar
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el producto: " + e);
            return false;
        }
    }

    // Reordenar los IDs visuales de productos
    public void reordenarIdsProductos() {
        try {
            conn = cn.getConnection();

            pst = conn.prepareStatement("SET @count = 0");
            pst.executeUpdate();

            pst = conn.prepareStatement("UPDATE productos SET id = (@count := @count + 1)");
            pst.executeUpdate();

            pst = conn.prepareStatement("ALTER TABLE productos AUTO_INCREMENT = 1");
            pst.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al reordenar IDs de productos: " + e);
        }
    }

    // Buscar producto por ID
    public Productos buscarProductoQuery(int id) {
        String query = "SELECT pro.*, ca.nombre AS nombre_categoria FROM productos pro "
                + "INNER JOIN categorias ca ON pro.categorias_id = ca.id WHERE pro.id=?";
        Productos product = new Productos();

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setCodigo(rs.getInt("codigo"));
                product.setNombre(rs.getString("nombre"));
                product.setDescripcion(rs.getString("descripcion"));
                product.setPrecio_unitario(rs.getDouble("precio_unitario"));
                product.setCategorias_id(rs.getInt("categorias_id"));
                product.setNombre_categoria(rs.getString("nombre_categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e);
        }
        return product;
    }

    // Buscar producto por código
    public Productos buscarCodigo(int codigo) {
        String query = "SELECT pro.id, pro.codigo, pro.nombre, pro.precio_unitario,"
                + " pro.cantidad_producto FROM productos pro WHERE pro.codigo = ?";
        Productos product = new Productos();

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, codigo);
            rs = pst.executeQuery();

            if (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setCodigo(rs.getInt("codigo"));
                product.setNombre(rs.getString("nombre"));
                product.setPrecio_unitario(rs.getDouble("precio_unitario"));
                product.setCantidad_producto(rs.getInt("cantidad_producto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar por código: " + e);
        }
        return product;
    }

    // Verificar cantidad disponible de un producto
    public Productos buscarId(int id) {
        Productos producto = new Productos();
        String query = "SELECT * FROM productos WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                // Validación defensiva por si viene NULL:
                Object cantidadObj = rs.getObject("cantidad_producto");
                int cantidad = (cantidadObj != null) ? rs.getInt("cantidad_producto") : 0;
                producto.setCantidad_producto(cantidad);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto por ID: " + e);
        }
        return producto;
    }

    // Actualizar stock del producto
    public boolean actualizarStockQuery(int monto, int producto_id) {
        String query = "UPDATE productos SET cantidad_producto = ? WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, monto);
            pst.setInt(2, producto_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e);
            return false;
        }
    }
}
