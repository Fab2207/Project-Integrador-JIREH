package Controladores;

import Models.Clientes;
import Models.ClientesDao;
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

public class ClientesController implements ActionListener, MouseListener, KeyListener {

    //Encapsular Variables
    private Clientes cliente;
    private ClientesDao cliente_dao;
    private Principal vista;
    DefaultTableModel model = new DefaultTableModel();

    public ClientesController(Clientes cliente, ClientesDao cliente_dao, Principal vista) {
        this.cliente = cliente;
        this.cliente_dao = cliente_dao;
        this.vista = vista;

        //Boton Registrar Cliente
        this.vista.btn_registrar_cliente.addActionListener(this);
        //Vincular la tabla
        this.vista.Tabla_Clientes.addMouseListener(this);
        //txt Buscar Cliente 
        this.vista.txt_buscar_cliente.addKeyListener(this);
        //Boton modificar Cliente
        this.vista.btn_editar_cliente.addActionListener(this);
        //Boton eliminar Cliente
        this.vista.btn_eliminar_cliente.addActionListener(this);
        //Boton cancelar 
        this.vista.btn_cancelar_cliente.addActionListener(this);
        //Vincular labels a las pestañas del sistema
        this.vista.jLabel5.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_registrar_cliente) {
            if (vista.txt_cliente_id.getText().equals("")
                    || vista.txt_cliente_name.getText().equals("")
                    || vista.txt_cliente_direccion.getText().equals("")
                    || vista.txt_cliente_celular.getText().equals("")
                    || vista.txt_cliente_email.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                cliente.setId(Integer.parseInt(vista.txt_cliente_id.getText().trim()));
                cliente.setNombre_completo(vista.txt_cliente_name.getText().trim());
                cliente.setDireccion(vista.txt_cliente_direccion.getText().trim());
                cliente.setCelular(vista.txt_cliente_celular.getText().trim());
                cliente.setCorreo_electronico(vista.txt_cliente_email.getText().trim());
                if (cliente_dao.registrarClienteQuery(cliente)) {
                    limpiarTablaCliente();
                    listAllClientes();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, "Se ha registrado al cliente con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente");
                }
            }
        } else if (e.getSource() == vista.btn_editar_cliente) {
            if (vista.txt_cliente_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para continuar");
            } else {
                if (vista.txt_cliente_id.getText().equals("")
                        || vista.txt_cliente_name.getText().equals("")
                        || vista.txt_cliente_direccion.getText().equals("")
                        || vista.txt_cliente_celular.getText().equals("")
                        || vista.txt_cliente_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    cliente.setId(Integer.parseInt(vista.txt_cliente_id.getText().trim()));
                    cliente.setNombre_completo(vista.txt_cliente_name.getText().trim());
                    cliente.setDireccion(vista.txt_cliente_direccion.getText().trim());
                    cliente.setCelular(vista.txt_cliente_celular.getText().trim());
                    cliente.setCorreo_electronico(vista.txt_cliente_email.getText().trim());
                }
                if (cliente_dao.actualizarClientesQuery(cliente)) {
                    limpiarTablaCliente();
                    listAllClientes();
                    vista.btn_registrar_cliente.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Datos modificados de forma exitosa");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el cliente");
                }
            }
        } else if (e.getSource() == vista.btn_eliminar_cliente) {
            int row = vista.Tabla_Clientes.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminarlo");
            } else {
                int id = Integer.parseInt(vista.Tabla_Clientes.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer eliminar este cliente?");
                if (question == 0 && cliente_dao.eliminarClienteQuery(id) != false) {
                    limpiarTablaCliente();
                    limpiarCampos();
                    listAllClientes();
                    vista.btn_registrar_cliente.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "El cliente fue eliminado de forma exitosa");
                }
            }
        } else if (e.getSource() == vista.btn_cancelar_cliente) {
            limpiarCampos();
            vista.btn_registrar_cliente.setEnabled(true);
            limpiarCampos();
        }
    }

    //Listar Clientes
    public void listAllClientes() {
        List<Clientes> list = cliente_dao.listarClientesQuery(vista.txt_buscar_cliente.getText());
        model = (DefaultTableModel) vista.Tabla_Clientes.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getNombre_completo();
            row[2] = list.get(i).getDireccion();
            row[3] = list.get(i).getCelular();
            row[4] = list.get(i).getCorreo_electronico();
            model.addRow(row);
        }
        vista.Tabla_Clientes.setModel(model);
    }

    //Limpiar Campos Clientes
    public void limpiarCampos() {
        vista.txt_cliente_id.setText("");
        vista.txt_cliente_id.setEditable(true);
        vista.txt_cliente_name.setText("");
        vista.txt_cliente_direccion.setText("");
        vista.txt_cliente_celular.setText("");
        vista.txt_cliente_email.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.Tabla_Clientes) {
            int row = vista.Tabla_Clientes.rowAtPoint(e.getPoint());
            vista.txt_cliente_id.setText(vista.Tabla_Clientes.getValueAt(row, 0).toString());
            vista.txt_cliente_name.setText(vista.Tabla_Clientes.getValueAt(row, 1).toString());
            vista.txt_cliente_direccion.setText(vista.Tabla_Clientes.getValueAt(row, 2).toString());
            vista.txt_cliente_celular.setText(vista.Tabla_Clientes.getValueAt(row, 3).toString());
            vista.txt_cliente_email.setText(vista.Tabla_Clientes.getValueAt(row, 4).toString());

            vista.txt_cliente_id.setEditable(false);
            vista.btn_registrar_cliente.setEnabled(false);
        } else if (e.getSource() == vista.jLabel5) {
            vista.jTabbedPane2.setSelectedIndex(3);
            limpiarTablaCliente();
            limpiarCampos();
            listAllClientes();
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
        if (e.getSource() == vista.txt_buscar_cliente) {
            limpiarTablaCliente();
            listAllClientes();
        }
    }

    public void limpiarTablaCliente() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i--;
        }
    }
}
