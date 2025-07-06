package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class VentasDao {

    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar Venta y retornar el ID generado
    public int registrarVentaQuery(int cliente_id, int empleado_id, double total) {
        String query = "INSERT INTO ventas (cliente_id, empleado_id, total, fecha_venta) VALUES (?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        int venta_id = -1;

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, cliente_id);
            pst.setInt(2, empleado_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                venta_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la venta: " + e);
        } finally {
            cerrarConexion();
        }
        return venta_id;
    }

    // Registrar Detalles de Venta
    public boolean registrarVentaDetallesQuery(int producto_id, int venta_id,
            int venta_cantidad, double venta_precio, double venta_subtotal) {
        String query = "INSERT INTO ventas_detalles (producto_id, venta_id, venta_cantidad, venta_precio, venta_subtotal) VALUES (?,?,?,?,?)";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, producto_id);
            pst.setInt(2, venta_id);
            pst.setInt(3, venta_cantidad);
            pst.setDouble(4, venta_precio);
            pst.setDouble(5, venta_subtotal);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalles de venta: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    // Listar ventas realizadas
    public List<Ventas> listaAllVentasQuery() {
        List<Ventas> list_ventas = new ArrayList<>();
        String query = "SELECT ve.id AS invoice, cli.nombre_completo AS clientes, em.nombre_completo AS empleados, ve.total, ve.fecha_venta FROM ventas ve INNER JOIN clientes cli ON ve.cliente_id = cli.id INNER JOIN empleados em ON ve.empleado_id = em.id ORDER BY ve.id ASC";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Ventas venta = new Ventas();
                venta.setId(rs.getInt("invoice"));
                venta.setCliente_nombre(rs.getString("clientes"));
                venta.setEmpleado_nombre(rs.getString("empleados"));
                venta.setTotal(rs.getDouble("total"));
                venta.setFecha_venta(rs.getString("fecha_venta"));
                list_ventas.add(venta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar ventas: " + e);
        } finally {
            cerrarConexion();
        }

        return list_ventas;
    }

    // Listar Ventas para imprimir Boleta
    public List<Ventas> listaVentasDetallesQuery(int id) {
        List<Ventas> list_ventas = new ArrayList<>();
        String query = "SELECT ve.fecha_venta, vede.venta_precio, vede.venta_cantidad, vede.venta_subtotal, " +
                      "cli.id AS cliente_id, cli.nombre_completo AS cliente_nombre, " +
                      "pro.nombre AS producto_nombre, em.nombre_completo AS empleado_nombre " +
                      "FROM ventas ve " +
                      "INNER JOIN ventas_detalles vede ON ve.id = vede.venta_id " +
                      "INNER JOIN productos pro ON vede.producto_id = pro.id " +
                      "INNER JOIN clientes cli ON ve.cliente_id = cli.id " +
                      "INNER JOIN empleados em ON ve.empleado_id = em.id " +
                      "WHERE ve.id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                Ventas venta = new Ventas();
                venta.setProducto_nombre(rs.getString("producto_nombre"));
                venta.setVenta_cantidad(rs.getInt("venta_cantidad"));
                venta.setVenta_precio(rs.getDouble("venta_precio"));
                venta.setVenta_subtotal(rs.getDouble("venta_subtotal"));
                venta.setCliente_id(rs.getInt("cliente_id"));
                venta.setCliente_nombre(rs.getString("cliente_nombre"));
                venta.setEmpleado_nombre(rs.getString("empleado_nombre"));
                venta.setFecha_venta(rs.getString("fecha_venta"));
                list_ventas.add(venta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles de venta: " + e);
        } finally {
            cerrarConexion();
        }
        return list_ventas;
    }

    // Cerrar conexiones y liberar recursos
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