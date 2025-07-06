package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientesDao {

    //Instancia Conexion
    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar Clientes
    public boolean registrarClienteQuery(Clientes cliente) {
        String query = "INSERT INTO clientes(id, nombre_completo, direccion, celular,"
                + "correo_electronico, crear_cliente, actualizar_cliente) "
                + "VALUES(?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, cliente.getId());
            pst.setString(2, cliente.getNombre_completo());
            pst.setString(3, cliente.getDireccion());
            pst.setString(4, cliente.getCelular());
            pst.setString(5, cliente.getCorreo_electronico());
            pst.setTimestamp(6, dateTime);
            pst.setTimestamp(7, dateTime);

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el cliente" + e);
            return false;
        }
    }

    //Listar Clientes
    public List listarClientesQuery(String value) {
        List<Clientes> list_clientes = new ArrayList();

        String query = "SELECT *FROM clientes";
        String query_buscar_cliente = "SELECT *FROM clientes WHERE id LIKE'%"
                + value + "%";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_buscar_cliente);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCelular(rs.getString("celular"));
                cliente.setCorreo_electronico(rs.getString("correo_electronico"));
                list_clientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_clientes;
    }

    //Modificar Clientes
    public boolean actualizarClientesQuery(Clientes cliente) {
        String query = "UPDATE clientes SET nombre_completo = ?, direccion = ?, celular = ?,"
                + "correo_electronico = ?, actualizar_cliente = ?" + "WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setString(1, cliente.getNombre_completo());
            pst.setString(2, cliente.getDireccion());
            pst.setString(3, cliente.getCelular());
            pst.setString(4, cliente.getCorreo_electronico());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, cliente.getId());

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar datos del cliente:" + e);
            return false;
        }
    }

    //Eliminar Clientes
    public boolean eliminarClienteQuery(int id) {
        String query = "DELETE FROM clientes WHERE id =" + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar un cliente"
                    + "que tenga relacion con otra tabla" + e);
            return false;
        }
    }

    //Buscar Cliente
    public Clientes buscarClientes(int id) {
        String query = "SELECT cli.id, cli.nombre_completo FROM clientes cli WHERE cli.id = ?";
        Clientes cliente = new Clientes();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cliente;
    }

}
