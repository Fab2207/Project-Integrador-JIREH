package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDao {

    //Instancia Conexion
    ConeccionMySQL cn = new ConeccionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Variables para el envio de datos entre las interfaces
    public static int id_user = 0;
    public static String nombre_user = "";
    public static String usuario_user = "";
    public static String direccion_user = "";
    public static String celular_user = "";
    public static String correo_user = "";
    public static String rol_user = "";

    //Metodo Login
    public Empleados loginQuery(String usuario, String contraseña) {
        String query = "SELECT*FROM empleados WHERE usuario = ? AND contraseña = ?";
        Empleados employee = new Empleados();

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            //Enviar parametros
            pst.setString(1, usuario);
            pst.setString(2, contraseña);

            rs = pst.executeQuery();

            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();

                employee.setNombre_completo(rs.getString("nombre_completo"));
                nombre_user = employee.getNombre_completo();

                employee.setUsuario(rs.getString("usuario"));
                usuario_user = employee.getUsuario();

                employee.setDireccion(rs.getString("direccion"));
                direccion_user = employee.getDireccion();

                employee.setCelular(rs.getString("celular"));
                celular_user = employee.getCelular();

                employee.setCorreo_electronico(rs.getString("correo_electronico"));
                correo_user = employee.getCorreo_electronico();

                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el empleado" + e);
        }
        return employee;
    }

    //Registrar empleado
    public boolean registrarEmpleadoQuery(Empleados employee) {
        String query = "INSERT into empleados(id, nombre_completo, usuario, "
                + "direccion, celular, correo_electronico, contraseña, rol, "
                + "fecha_registro, actualizar_informacion) VALUES(?,?,?,?,?,?,?,?,?,?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);

            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getNombre_completo());
            pst.setString(3, employee.getUsuario());
            pst.setString(4, employee.getDireccion());
            pst.setString(5, employee.getCelular());
            pst.setString(6, employee.getCorreo_electronico());
            pst.setString(7, employee.getContraseña());
            pst.setString(8, employee.getRol());

            pst.setTimestamp(9, dateTime);
            pst.setTimestamp(10, dateTime);

            pst.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar empleado: " + e);
            return false;
        }
    }

    //Listar empleado
    public List listaEmpleadosQuery(String value) {
        List<Empleados> list_employees = new ArrayList();
        String query = "SELECT *FROM empleados ORDER BY rol ASC";
        String query_buscar_empleado = "SELECT *FROM empleados WHERE id LIKE ?";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_buscar_empleado);
                pst.setString(1, "%" + value + "%");
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Empleados employee = new Empleados();

                employee.setId(rs.getInt("id"));
                employee.setNombre_completo(rs.getString("nombre_completo"));
                employee.setUsuario(rs.getString("usuario"));
                employee.setDireccion(rs.getString("direccion"));
                employee.setCelular(rs.getString("celular"));
                employee.setCorreo_electronico(rs.getString("correo_electronico"));
                employee.setRol(rs.getString("rol"));

                list_employees.add(employee);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }

    //Modificar empleado
    public boolean modificarEmpleadoQuery(Empleados employee) {
        String query = "UPDATE empleados SET nombre_completo = ?, usuario= ?,"
                + "direccion = ?, celular = ?, correo_electronico = ?,"
                + "rol = ?, actualizar_informacion = ?" + "WHERE id = ?";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getNombre_completo());
            pst.setString(2, employee.getUsuario());
            pst.setString(3, employee.getDireccion());
            pst.setString(4, employee.getCelular());
            pst.setString(5, employee.getCorreo_electronico());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, employee.getId());

            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el empleado:"
                    + e);
            return false;
        }
    }

    public boolean eliminarEmpleadoQuery(int id) {
        String query = "DELETE FROM empleados WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar un empleado "
                    + "que tenga relación con otra tabla." + e.getMessage());
            return false;
        }
    }

    //Cambiar Contraseña
    public boolean modificarEmpleadoPassword(Empleados employee) {
        String query = "UPDATE empleados SET contraseña = ? WHERE usuario ='"
                + usuario_user + "'";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getContraseña());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al cambiar "
                    + "la contraseña" + e);
            return false;
        }
    }
}
