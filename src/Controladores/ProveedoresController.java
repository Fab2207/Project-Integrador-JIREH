package Controladores;

import Models.ComboBoxDinamico;
import static Models.EmpleadosDao.rol_user;
import Models.Proveedores;
import Models.ProveedoresDao;
import Vistas.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProveedoresController implements ActionListener, MouseListener, KeyListener {

    //Encapsular Variables
    private Proveedores proveedor;
    private ProveedoresDao proveedor_dao;
    private Principal vista;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProveedoresController(Proveedores proveedor, ProveedoresDao proveedor_dao, Principal vista) {
        this.proveedor = proveedor;
        this.proveedor_dao = proveedor_dao;
        this.vista = vista;

        //Boton Registrar Proveedor
        this.vista.btn_registrar_proveedor.addActionListener(this);
        //Vincular la tabla
        this.vista.Tabla_Proveedor.addMouseListener(this);
        //txt Buscar Proveedor
        this.vista.txt_buscar_proveedor.addKeyListener(this);
        //Boton modificar Proveedor
        this.vista.btn_editar_proveedor.addActionListener(this);
        //Boton eliminar Proveedor
        this.vista.btn_eliminar_proveedor.addActionListener(this);
        //Boton cancelar 
        this.vista.btn_cancelar_proveedor.addActionListener(this);
        //Vincular labels a las pestañas del sistema
        this.vista.jLabel7.addMouseListener(this);
        //Metodo Proveedores Nombre
        getProveedoresNombre();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // REGISTRAR PROVEEDOR
        if (e.getSource() == vista.btn_registrar_proveedor) {
            if (vista.txt_proveedor_name.getText().equals("")
                    || vista.txt_proveedor_descripcion.getText().equals("")
                    || vista.txt_proveedor_direccion.getText().equals("")
                    || vista.txt_proveedor_celular.getText().equals("")
                    || vista.txt_proveedor_correo.getText().equals("")
                    || vista.cmb_distrito.getSelectedItem().toString().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                proveedor.setNombre(vista.txt_proveedor_name.getText().trim());
                proveedor.setDescripcion(vista.txt_proveedor_descripcion.getText().trim());
                proveedor.setDireccion(vista.txt_proveedor_direccion.getText().trim());
                proveedor.setCelular(vista.txt_proveedor_celular.getText().trim());
                proveedor.setEmail(vista.txt_proveedor_correo.getText().trim());
                proveedor.setDistrito(vista.cmb_distrito.getSelectedItem().toString());

                if (proveedor_dao.registrarProveedoresQuery(proveedor)) {
                    limpiarTablaProveedor();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
                    limpiarCampos();
                    listAllProveedores();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el proveedor");
                }
            }

            // EDITAR PROVEEDOR
        } else if (e.getSource() == vista.btn_editar_proveedor) {
            if (vista.txt_proveedor_id.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para continuar");
            } else {
                if (vista.txt_proveedor_name.getText().equals("")
                        || vista.txt_proveedor_descripcion.getText().equals("")
                        || vista.txt_proveedor_direccion.getText().equals("")
                        || vista.txt_proveedor_celular.getText().equals("")
                        || vista.txt_proveedor_correo.getText().equals("")
                        || vista.cmb_distrito.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    proveedor.setId(Integer.parseInt(vista.txt_proveedor_id.getText().trim()));
                    proveedor.setNombre(vista.txt_proveedor_name.getText().trim());
                    proveedor.setDescripcion(vista.txt_proveedor_descripcion.getText().trim());
                    proveedor.setDireccion(vista.txt_proveedor_direccion.getText().trim());
                    proveedor.setCelular(vista.txt_proveedor_celular.getText().trim());
                    proveedor.setEmail(vista.txt_proveedor_correo.getText().trim());
                    proveedor.setDistrito(vista.cmb_distrito.getSelectedItem().toString());

                    if (proveedor_dao.actualizarProveedoresQuery(proveedor)) {
                        limpiarTablaProveedor();
                        listAllProveedores();
                        JOptionPane.showMessageDialog(null, "Datos del proveedor modificados correctamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar los datos del proveedor");
                    }
                }
            }

            //  ELIMINAR PROVEEDOR 
        } else if (e.getSource() == vista.btn_eliminar_proveedor) {
            int fila = vista.Tabla_Proveedor.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminarlo");
            } else {
                int id = Integer.parseInt(vista.Tabla_Proveedor.getValueAt(fila, 0).toString());
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer eliminar este proveedor?");
                if (confirmacion == 0 && proveedor_dao.eliminarProveedoresQuery(id)) {
                    limpiarTablaProveedor();
                    limpiarCampos();
                    listAllProveedores();
                    vista.btn_registrar_proveedor.setEnabled(true);
                    JOptionPane.showMessageDialog(null, " Proveedor eliminado correctamente");
                }
            }

            //  CANCELAR PROVEEDOR
        } else if (e.getSource() == vista.btn_cancelar_proveedor) {
            limpiarCampos();
            vista.btn_registrar_proveedor.setEnabled(true);
            vista.txt_proveedor_id.setEditable(true);
            vista.txt_proveedor_name.requestFocus();
            vista.Tabla_Proveedor.clearSelection();
        }
    }

    //Listar Clientes
    public void listAllProveedores() {
        List<Proveedores> list = proveedor_dao.listarProveedoresQuery(vista.txt_buscar_proveedor.getText());
        model = (DefaultTableModel) vista.Tabla_Proveedor.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getNombre();
            row[2] = list.get(i).getDescripcion();
            row[3] = list.get(i).getDireccion();
            row[4] = list.get(i).getCelular();
            row[5] = list.get(i).getEmail();
            row[6] = list.get(i).getDistrito();
            model.addRow(row);
        }
        vista.Tabla_Proveedor.setModel(model);
    }

    //Limpiar Campos
    public void limpiarCampos() {
        vista.txt_proveedor_id.setText("");
        vista.txt_proveedor_id.setEditable(true);
        vista.txt_proveedor_name.setText("");
        vista.txt_proveedor_descripcion.setText("");
        vista.txt_proveedor_direccion.setText("");
        vista.txt_proveedor_celular.setText("");
        vista.txt_proveedor_correo.setText("");
        vista.cmb_distrito.setSelectedIndex(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.Tabla_Proveedor) {
            int row = vista.Tabla_Proveedor.rowAtPoint(e.getPoint());
            if (row >= 0) {
                try {
                    vista.txt_proveedor_id.setText(vista.Tabla_Proveedor.getValueAt(row, 0).toString());
                    vista.txt_proveedor_name.setText(vista.Tabla_Proveedor.getValueAt(row, 1).toString());
                    vista.txt_proveedor_descripcion.setText(vista.Tabla_Proveedor.getValueAt(row, 2).toString());
                    vista.txt_proveedor_direccion.setText(vista.Tabla_Proveedor.getValueAt(row, 3).toString());
                    vista.txt_proveedor_celular.setText(vista.Tabla_Proveedor.getValueAt(row, 4).toString());
                    vista.txt_proveedor_correo.setText(vista.Tabla_Proveedor.getValueAt(row, 5).toString());
                    vista.cmb_distrito.setSelectedItem(vista.Tabla_Proveedor.getValueAt(row, 6).toString());

                    vista.txt_proveedor_id.setEditable(false);
                    vista.btn_registrar_proveedor.setEnabled(false);

                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Algunos datos de la fila seleccionada están vacíos.");
                }
            }
        } else if (e.getSource() == vista.jLabel7) {
            if (rol.equals("Administrador")) {
                vista.jTabbedPane2.setSelectedIndex(5);
                limpiarTablaProveedor();
                limpiarCampos();
                listAllProveedores();
            } else {
                vista.jTabbedPane2.setEnabledAt(5, false);
                JOptionPane.showMessageDialog(null, "El usuario no tiene privilegios de Administrador");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txt_buscar_proveedor) {
            limpiarTablaProveedor();
            listAllProveedores();
        }
    }

    public void limpiarTablaProveedor() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i--;
        }
    }

    public void getProveedoresNombre() {
        vista.cmb_Producto_Categoria.removeAllItems();
        List<Proveedores> list = proveedor_dao.listarProveedoresQuery("");
        for (Proveedores prov : list) {
            vista.cmb_compras_Proveedor.addItem(new ComboBoxDinamico(prov.getId(), prov.getNombre()));
        }
    }

}
