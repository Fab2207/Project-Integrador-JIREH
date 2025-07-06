package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ComprasDao {

    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar Compra y devolver el ID generado
    public int registrarCompraQuery(int proveedor_id, int empleados_id, double total) {
        String query = "INSERT INTO compras (proveedor_id, empleados_id, total, crear_compra) VALUES (?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        int id_generado = -1;

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, proveedor_id);
            pst.setInt(2, empleados_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id_generado = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra: " + e);
        } finally {
            cerrarConexion();
        }
        return id_generado;
    }

    //Registrar Detalles de la Compra
    public boolean registrarCompraDetallesQuery(int compras_id, double compras_precio,
            int compras_cantidad, double compras_subtotal, int productos_id) {
        String query = "INSERT INTO compras_detalles (compras_id, compras_precio, compras_cantidad, compras_subtotal, productos_id) VALUES (?,?,?,?,?)";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, compras_id);
            pst.setDouble(2, compras_precio);
            pst.setInt(3, compras_cantidad);
            pst.setDouble(4, compras_subtotal);
            pst.setInt(5, productos_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalles de la compra: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    //Listar Compras realizadas
    public List<Compras> listaAllComprasQuery() {
        List<Compras> list_compras = new ArrayList<>();
        String query = "SELECT co.*, prov.nombre AS nombre_proveedor FROM compras co INNER JOIN proveedores prov ON co.proveedor_id = prov.id ORDER BY co.id ASC";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Compras compra = new Compras();
                compra.setId(rs.getInt("id"));
                compra.setNombre_proveedor_producto(rs.getString("nombre_proveedor"));
                compra.setTotal(rs.getDouble("total"));
                compra.setCrear_compra(rs.getString("crear_compra"));
                list_compras.add(compra);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarConexion();
        }
        return list_compras;
    }

    //Listar Compras para imprimir Factura
    public List<Compras> listaComprasDetallesQuery(int id) {
        List<Compras> list_compras = new ArrayList<>();
        String query = "SELECT co.crear_compra, comde.compras_precio, comde.compras_cantidad, comde.compras_subtotal, prov.nombre AS proveedor_nombre, pro.nombre AS producto_nombre, em.nombre_completo FROM compras co INNER JOIN compras_detalles comde ON co.id = comde.compras_id INNER JOIN productos pro ON comde.productos_id = pro.id INNER JOIN proveedores prov ON co.proveedor_id = prov.id INNER JOIN empleados em ON co.empleados_id = em.id WHERE co.id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                Compras compra = new Compras();
                compra.setNombre_producto(rs.getString("producto_nombre"));
                compra.setCompras_cantidad(rs.getInt("compras_cantidad"));
                compra.setCompras_precio(rs.getDouble("compras_precio"));
                compra.setCompras_subtotal(rs.getDouble("compras_subtotal"));
                compra.setNombre_proveedor_producto(rs.getString("proveedor_nombre"));
                compra.setCrear_compra(rs.getString("crear_compra"));
                compra.setCompra(rs.getString("nombre_completo"));
                list_compras.add(compra);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            cerrarConexion();
        }
        return list_compras;
    }

    //Cerrar conexiones
    private void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e);
        }
    }
}
