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

public class ProveedoresDao {

    //Instancia Conexion
    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar Proveedor
    public boolean registrarProveedoresQuery(Proveedores proveedor) {
        String query = "INSERT INTO proveedores(nombre, descripcion, direccion, "
                + "celular, email, distrito, fecha_registro, actualizar_informacion) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, proveedor.getNombre());
            pst.setString(2, proveedor.getDescripcion());
            pst.setString(3, proveedor.getDireccion());
            pst.setString(4, proveedor.getCelular());
            pst.setString(5, proveedor.getEmail());
            pst.setString(6, proveedor.getDistrito());
            pst.setTimestamp(7, dateTime);
            pst.setTimestamp(8, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el proveedor:" + e);
            return false;
        }
    }

    //Listar Proveedor
    public List listarProveedoresQuery(String value) {
        List<Proveedores> list_proveedores = new ArrayList();

        String query = "SELECT *FROM proveedores";
        String query_buscar_proveedor = "SELECT *FROM proveedores WHERE nombre "
                + "LIKE'%" + value + "%";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_buscar_proveedor);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDescripcion(rs.getString("descripcion"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setCelular(rs.getString("celular"));
                proveedor.setEmail(rs.getString("email"));
                proveedor.setDistrito(rs.getString("distrito"));
                list_proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_proveedores;
    }

    //Modificar Proveedor
    public boolean actualizarProveedoresQuery(Proveedores proveedor) {
        String query = "UPDATE proveedores SET nombre = ?, descripcion = ?, "
                + "direccion = ?, celular = ?, email = ?, distrito = ?, "
                + "actualizar_informacion = ? WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, proveedor.getNombre());
            pst.setString(2, proveedor.getDescripcion());
            pst.setString(3, proveedor.getDireccion());
            pst.setString(4, proveedor.getCelular());
            pst.setString(5, proveedor.getEmail());
            pst.setString(6, proveedor.getDistrito());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, proveedor.getId());

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar datos del "
                    + "proveedor" + e);
            return false;
        }
    }

    //Eliminar Proveedor
    public boolean eliminarProveedoresQuery(int id) {
        String query = "DELETE FROM proveedores WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.executeUpdate();

            // Reorganizar IDs después de la eliminación
            reordenarIdsProveedores();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedor: " + e);
            return false;
        }
    }

    public void reordenarIdsProveedores() {
        String resetContador = "SET @count = 0";
        String reordenar = "UPDATE proveedores SET id = (@count := @count + 1)";
        String reiniciarAI = "ALTER TABLE proveedores AUTO_INCREMENT = 1";

        try {
            conn = cn.getConnection();

            // Resetear contador de ID
            pst = conn.prepareStatement(resetContador);
            pst.executeUpdate();

            // Reasignar IDs en orden
            pst = conn.prepareStatement(reordenar);
            pst.executeUpdate();

            // Reiniciar AUTO_INCREMENT
            pst = conn.prepareStatement(reiniciarAI);
            pst.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al reordenar IDs de proveedores: " + e);
        }
    }
}
