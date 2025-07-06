package Controladores;

import Models.Empleados;
import Models.EmpleadosDao;
import static Models.EmpleadosDao.id_user;
import static Models.EmpleadosDao.rol_user;
import Vistas.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmpleadosController implements ActionListener, MouseListener, KeyListener {

    private Empleados empleado;
    private EmpleadosDao empleado_dao;
    private Principal vista;
    String rol = rol_user;
    DefaultTableModel modelo = new DefaultTableModel();

    public EmpleadosController(Empleados empleado, EmpleadosDao empleado_dao, Principal vista) {
        this.empleado = empleado;
        this.empleado_dao = empleado_dao;
        this.vista = vista;
        //Boton Registrar empleado
        this.vista.btn_registrar_empleado.addActionListener(this);
        //Vincular la tabla
        this.vista.Tabla_Empleados.addMouseListener(this);
        //txt Buscar tabla empleado
        this.vista.txt_buscar_empleado.addKeyListener(this);
        //Boton modificar Empleado
        this.vista.btn_editar_empleado.addActionListener(this);
        //Boton eliminar Empleado
        this.vista.btn_eliminar_empleado.addActionListener(this);
        //Boton cancelar 
        this.vista.btn_cancelar_empleado.addActionListener(this);
        //Boton modificar contraseña
        this.vista.btn_modificar_perfil.addActionListener(this);
        //Vincular labels a las pestañas del sistema
        this.vista.jLabel6.addMouseListener(this);
    }

    //Registrar Empleado controlador
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_registrar_empleado) {
            if (vista.txt_empleados_id.getText().equals("")
                    || vista.txt_empleados_name.getText().equals("")
                    || vista.txt_empleados_usuario.getText().equals("")
                    || vista.txt_empleados_direccion.getText().equals("")
                    || vista.txt_empleados_celular.getText().equals("")
                    || vista.txt_empleados_correo.getText().equals("")
                    || vista.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(vista.txt_empleados_password.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                empleado.setId(Integer.parseInt(vista.txt_empleados_id.getText().trim()));
                empleado.setNombre_completo(vista.txt_empleados_name.getText().trim());
                empleado.setUsuario(vista.txt_empleados_usuario.getText().trim());
                empleado.setDireccion(vista.txt_empleados_direccion.getText().trim());
                empleado.setCelular(vista.txt_empleados_celular.getText().trim());
                empleado.setCorreo_electronico(vista.txt_empleados_correo.getText().trim());
                empleado.setContraseña(String.valueOf(vista.txt_empleados_password.getPassword()));
                empleado.setRol(vista.cmb_rol.getSelectedItem().toString());

                if (empleado_dao.registrarEmpleadoQuery(empleado)) {
                    limpiarTablaEmpleado();
                    JOptionPane.showMessageDialog(null, "El empleado fue registrado con éxito");
                    limpiarCampos();
                    listaAllEmpleados();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el empleado");
                }
            }
        } else if (e.getSource() == vista.btn_editar_empleado) {
            if (vista.txt_empleados_id.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para continuar");
            } else {
                if (vista.txt_empleados_id.getText().equals("")
                        || vista.txt_empleados_name.getText().equals("")
                        || vista.cmb_rol.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    empleado.setId(Integer.parseInt(vista.txt_empleados_id.getText().trim()));
                    empleado.setNombre_completo(vista.txt_empleados_name.getText().trim());
                    empleado.setUsuario(vista.txt_empleados_usuario.getText().trim());
                    empleado.setDireccion(vista.txt_empleados_direccion.getText().trim());
                    empleado.setCelular(vista.txt_empleados_celular.getText().trim());
                    empleado.setCorreo_electronico(vista.txt_empleados_correo.getText().trim());
                    empleado.setContraseña(String.valueOf(vista.txt_empleados_password.getPassword()));
                    empleado.setRol(vista.cmb_rol.getSelectedItem().toString());
                    if (empleado_dao.modificarEmpleadoQuery(empleado)) {
                        limpiarTablaEmpleado();
                        listaAllEmpleados();
                        vista.btn_registrar_empleado.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados de forma exitosa");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el empleado");
                    }
                }
            }
        } else if (e.getSource() == vista.btn_eliminar_empleado) {
            int row = vista.Tabla_Empleados.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminarlo");
            } else if (vista.Tabla_Empleados.getValueAt(row, 0).equals(id_user)) {
                JOptionPane.showMessageDialog(null, "No puede eliminar al usuario autenticado");
            } else {
                int id = Integer.parseInt(vista.Tabla_Empleados.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer eliminar este empleado?");
                if (question == 0 && empleado_dao.eliminarEmpleadoQuery(id) != false) {
                    limpiarTablaEmpleado();
                    limpiarCampos();
                    vista.btn_registrar_empleado.setEnabled(true);
                    vista.txt_empleados_password.setEnabled(true);
                    listaAllEmpleados();
                    JOptionPane.showMessageDialog(null, "El usuario fue eliminado de forma exitosa");
                }
            }
        } else if (e.getSource() == vista.btn_cancelar_empleado) {
            limpiarCampos();
            vista.btn_registrar_empleado.setEnabled(true);
            vista.txt_empleados_password.setEnabled(true);
            vista.txt_empleados_password.setEditable(true);
            vista.txt_empleados_id.setEditable(true);
            vista.txt_empleados_id.setEnabled(true);
            vista.Tabla_Empleados.clearSelection();

        } else if (e.getSource() == vista.btn_modificar_perfil) {
            String password = String.valueOf(vista.txt_perfil_password_nueva.getPassword());
            String password_confirmar = String.valueOf(vista.txt_perfil_password_confirmar.getPassword());
            if (!password.equals("") && !password_confirmar.equals("")) {
                if (password.equals(password_confirmar)) {
                    empleado.setContraseña(String.valueOf(vista.txt_perfil_password_nueva.getPassword()));
                    if (empleado_dao.modificarEmpleadoPassword(empleado) != false) {
                        JOptionPane.showMessageDialog(null, "Se modifico correctamente la contraseña");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }
        }
    }

    //Listar todos los empleados
    public void listaAllEmpleados() {
        if (rol.equals("Administrador")) {
            List<Empleados> list = empleado_dao.listaEmpleadosQuery(vista.txt_buscar_empleado.getText());
            modelo = (DefaultTableModel) vista.Tabla_Empleados.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getNombre_completo();
                row[2] = list.get(i).getUsuario();
                row[3] = list.get(i).getDireccion();
                row[4] = list.get(i).getCelular();
                row[5] = list.get(i).getCorreo_electronico();
                row[6] = list.get(i).getRol();
                modelo.addRow(row);
            }
        }
    }

    //Limpiar Campos
    public void limpiarCampos() {
        vista.txt_empleados_id.setText("");
        vista.txt_empleados_id.setEditable(true);
        vista.txt_empleados_name.setText("");
        vista.txt_empleados_usuario.setText("");
        vista.txt_empleados_direccion.setText("");
        vista.txt_empleados_celular.setText("");
        vista.txt_empleados_correo.setText("");
        vista.txt_empleados_password.setText("");
        vista.txt_empleados_password.setEnabled(true);
        vista.cmb_rol.setSelectedIndex(0);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource() == vista.Tabla_Empleados) {
            int row = vista.Tabla_Empleados.rowAtPoint(e.getPoint());
            if (row >= 0) {
                try {
                    vista.txt_empleados_id.setText(vista.Tabla_Empleados.getValueAt(row, 0).toString());
                    vista.txt_empleados_name.setText(vista.Tabla_Empleados.getValueAt(row, 1).toString());
                    vista.txt_empleados_usuario.setText(vista.Tabla_Empleados.getValueAt(row, 2).toString());
                    vista.txt_empleados_direccion.setText(vista.Tabla_Empleados.getValueAt(row, 3).toString());
                    vista.txt_empleados_celular.setText(vista.Tabla_Empleados.getValueAt(row, 4).toString());
                    vista.txt_empleados_correo.setText(vista.Tabla_Empleados.getValueAt(row, 5).toString());
                    vista.cmb_rol.setSelectedItem(vista.Tabla_Empleados.getValueAt(row, 6).toString());

                    vista.txt_empleados_id.setEditable(false);
                    vista.txt_empleados_password.setEditable(false);
                    vista.btn_registrar_empleado.setEnabled(false);

                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Algunos datos de la fila seleccionada están vacíos.");
                }
            }
        } else if (e.getSource() == vista.jLabel6) {
            if (rol.equals("Administrador")) {
                vista.jTabbedPane2.setSelectedIndex(4);
                limpiarTablaEmpleado();
                limpiarCampos();
                listaAllEmpleados();
            } else {
                vista.jTabbedPane2.setEnabledAt(4, false);
                JOptionPane.showMessageDialog(null, "El usuario "
                        + "no tienes privilegios de Administrador");
            }
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txt_buscar_empleado) {
            limpiarTablaEmpleado();
            listaAllEmpleados();
        }
    }

    //Metodo vaciar tablas
    public void limpiarTablaEmpleado() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }

}
