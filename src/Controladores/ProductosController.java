package Controladores;

import Models.ComboBoxDinamico;
import static Models.EmpleadosDao.rol_user;
import Models.Productos;
import Models.ProductosDao;
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

public class ProductosController implements ActionListener, MouseListener, KeyListener {

    private Productos producto;
    private ProductosDao producto_dao;
    private Principal vista;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProductosController(Productos producto, ProductosDao producto_dao, Principal vista) {
        this.producto = producto;
        this.producto_dao = producto_dao;
        this.vista = vista;

        this.vista.btn_Registrar_Producto.addActionListener(this);
        this.vista.Tabla_Productos.addMouseListener(this);
        this.vista.txt_Buscar_Producto.addKeyListener(this);
        this.vista.btn_Actualizar_Producto.addActionListener(this);
        this.vista.btn_Eliminar_Producto.addActionListener(this);
        this.vista.btn_Cancelar_Producto.addActionListener(this);
        this.vista.jLabel3.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // REGISTRAR PRODUCTO
        if (e.getSource() == vista.btn_Registrar_Producto) {
            if (vista.txt_Producto_Codigo.getText().equals("")
                    || vista.txt_Producto_Nombre.getText().equals("")
                    || vista.txt_Producto_Descripcion.getText().equals("")
                    || vista.txt_Producto_Precio.getText().equals("")
                    || vista.cmb_Producto_Categoria.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                producto.setCodigo(Integer.parseInt(vista.txt_Producto_Codigo.getText()));
                producto.setNombre(vista.txt_Producto_Nombre.getText().trim());
                producto.setDescripcion(vista.txt_Producto_Descripcion.getText().trim());
                producto.setPrecio_unitario(Double.parseDouble(vista.txt_Producto_Precio.getText().trim()));
                ComboBoxDinamico categoria_id = (ComboBoxDinamico) vista.cmb_Producto_Categoria.getSelectedItem();
                producto.setCategorias_id(categoria_id.getId());

                if (producto_dao.registrarProductoQuery(producto)) {
                    limpiarTabla();
                    limpiarCampos();
                    listAllProductos();
                    JOptionPane.showMessageDialog(null, "Producto registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar producto");
                }
            }

            // MODIFICAR PRODUCTO
        } else if (e.getSource() == vista.btn_Actualizar_Producto) {
            if (vista.txt_Producto_Codigo.getText().equals("")
                    || vista.txt_Producto_Nombre.getText().equals("")
                    || vista.txt_Producto_Descripcion.getText().equals("")
                    || vista.txt_Producto_Precio.getText().equals("")
                    || vista.cmb_Producto_Categoria.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                producto.setCodigo(Integer.parseInt(vista.txt_Producto_Codigo.getText()));
                producto.setNombre(vista.txt_Producto_Nombre.getText().trim());
                producto.setDescripcion(vista.txt_Producto_Descripcion.getText().trim());
                producto.setPrecio_unitario(Double.parseDouble(vista.txt_Producto_Precio.getText()));
                ComboBoxDinamico categoria_id = (ComboBoxDinamico) vista.cmb_Producto_Categoria.getSelectedItem();
                producto.setCategorias_id(categoria_id.getId());
                producto.setId(Integer.parseInt(vista.txt_Producto_ID.getText()));

                if (producto_dao.actualizarProductoQuery(producto)) {
                    limpiarTabla();
                    limpiarCampos();
                    listAllProductos();
                    vista.btn_Registrar_Producto.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Producto modificado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el producto");
                }
            }

            // ELIMINAR PRODUCTO
        } else if (e.getSource() == vista.btn_Eliminar_Producto) {
            int row = vista.Tabla_Productos.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un producto para poder eliminarlo");
            } else {
                int id = Integer.parseInt(vista.Tabla_Productos.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar este producto?");
                if (question == 0) {
                    boolean eliminado = producto_dao.eliminarProductoQuery(id);
                    if (eliminado) {
                        limpiarCampos();
                        limpiarTabla();
                        listAllProductos();
                        vista.btn_Eliminar_Producto.setEnabled(true);
                        vista.btn_Registrar_Producto.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Producto eliminado y IDs reorganizados correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto. Puede estar relacionado con otra entidad.");
                    }
                }
            }

            // CANCELAR PRODUCTO
        } else if (e.getSource() == vista.btn_Cancelar_Producto) {
            limpiarCampos();
            vista.btn_Cancelar_Producto.setEnabled(true);
            vista.btn_Registrar_Producto.setEnabled(true);
        }
    }

    public void listAllProductos() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Productos> list = producto_dao.listarProductoQuery(vista.txt_Buscar_Producto.getText());

            //Ordenar por ID ascendente
            list.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));

            model = (DefaultTableModel) vista.Tabla_Productos.getModel();
            Object row[] = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCodigo();
                row[2] = list.get(i).getNombre();
                row[3] = list.get(i).getDescripcion();
                row[4] = list.get(i).getPrecio_unitario();
                row[5] = list.get(i).getCantidad_producto();
                row[6] = list.get(i).getNombre_categoria();
                model.addRow(row);
            }
            vista.Tabla_Productos.setModel(model);

            // Restringir controles para rol Auxiliar
            if (rol.equals("Auxiliar")) {
                vista.btn_Registrar_Producto.setEnabled(false);
                vista.btn_Actualizar_Producto.setEnabled(false);
                vista.btn_Eliminar_Producto.setEnabled(false);
                vista.btn_Cancelar_Producto.setEnabled(false);
                vista.txt_Producto_Codigo.setEditable(false);
                vista.txt_Producto_ID.setEditable(false);
                vista.txt_Producto_Nombre.setEditable(false);
                vista.txt_Producto_Descripcion.setEditable(false);
                vista.txt_Producto_Precio.setEditable(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.Tabla_Productos) {
            int row = vista.Tabla_Productos.rowAtPoint(e.getPoint());
            vista.txt_Producto_ID.setText(vista.Tabla_Productos.getValueAt(row, 0).toString());
            producto = producto_dao.buscarProductoQuery(Integer.parseInt(vista.txt_Producto_ID.getText()));
            vista.txt_Producto_Codigo.setText("" + producto.getCodigo());
            vista.txt_Producto_Nombre.setText(producto.getNombre());
            vista.txt_Producto_Descripcion.setText(producto.getDescripcion());
            vista.txt_Producto_Precio.setText("" + producto.getPrecio_unitario());
            vista.cmb_Producto_Categoria.setSelectedItem(new ComboBoxDinamico(producto.getCategorias_id(), producto.getNombre_categoria()));
            vista.btn_Registrar_Producto.setEnabled(false);
        } else if (e.getSource() == vista.jLabel3) {
            vista.jTabbedPane2.setSelectedIndex(0);
            limpiarCampos();
            limpiarTabla();
            listAllProductos();
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
        if (e.getSource() == vista.txt_Buscar_Producto) {
            limpiarTabla();
            listAllProductos();
        }
    }

    public void limpiarTabla() {
        model = (DefaultTableModel) vista.Tabla_Productos.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void limpiarCampos() {
        vista.txt_Producto_ID.setText("");
        vista.txt_Producto_Codigo.setText("");
        vista.txt_Producto_Nombre.setText("");
        vista.txt_Producto_Descripcion.setText("");
        vista.txt_Producto_Precio.setText("");
    }

}
