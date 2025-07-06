package Controladores;

import Models.Categorias;
import Models.CategoriasDao;
import Models.ComboBoxDinamico;
import Models.EmpleadosDao;
import static Models.EmpleadosDao.rol_user;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class CategoriasController implements ActionListener, MouseListener, KeyListener {

    private Categorias categoria;
    private CategoriasDao categoria_dao;
    private Principal vista;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriasController(Categorias categoria, CategoriasDao categoria_dao, Principal vista) {
        this.categoria = categoria;
        this.categoria_dao = categoria_dao;
        this.vista = vista;

        // Enlazar botones con acciones
        this.vista.btn_registrar_categoria.addActionListener(this);
        this.vista.btn_editar_categoria.addActionListener(this);
        this.vista.btn_eliminar_categoria.addActionListener(this);
        this.vista.btn_cancelar_categoria.addActionListener(this);

        // Enlazar eventos visuales
        this.vista.Tabla_Categoria.addMouseListener(this);
        this.vista.jLabel8.addMouseListener(this);
        this.vista.txt_buscar_categoria.addKeyListener(this);

        // Decorar ComboBox con autocompletado
        AutoCompleteDecorator.decorate(vista.cmb_Producto_Categoria);

        // Cargar opciones en ComboBox desde BD
        getCategoriasNombre();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // REGISTRAR CATEGORÍA
        if (e.getSource() == vista.btn_registrar_categoria) {
            if (vista.txt_categoria_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo de nombre es obligatorio");
            } else {
                categoria.setNombre(vista.txt_categoria_name.getText().trim());
                if (categoria_dao.registrarCategoriaQuery(categoria)) {
                    limpiarTabla();
                    limpiarCampos();
                    listAllCategorias();
                    JOptionPane.showMessageDialog(null, "Categoría registrada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoría");
                }
            }

            // MODIFICAR CATEGORÍA
        } else if (e.getSource() == vista.btn_editar_categoria) {
            if (vista.txt_categoria_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (vista.txt_categoria_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    categoria.setId(Integer.parseInt(vista.txt_categoria_id.getText()));
                    categoria.setNombre(vista.txt_categoria_name.getText().trim());
                    if (categoria_dao.actualizarCategoriaQuery(categoria)) {
                        limpiarTabla();
                        limpiarCampos();
                        listAllCategorias();
                        vista.btn_editar_categoria.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Categoría modificada correctamente");
                    }
                }
            }

            // ELIMINAR CATEGORÍA
        } else if (e.getSource() == vista.btn_eliminar_categoria) {
            int row = vista.Tabla_Categoria.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione la categoría a eliminar");
            } else {
                int id = Integer.parseInt(vista.Tabla_Categoria.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar esta categoría?");
                if (question == 0) {
                    boolean eliminada = categoria_dao.eliminarCategoriaQuery(id);
                    if (eliminada) {
                        limpiarCampos();
                        limpiarTabla();
                        listAllCategorias();
                        vista.btn_eliminar_categoria.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Categoría eliminada correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, " No se pudo eliminar la categoría. Puede que tenga productos asociados.");
                    }
                }
            }

            // CANCELAR
        } else if (e.getSource() == vista.btn_cancelar_categoria) {
            limpiarCampos();
            vista.btn_cancelar_categoria.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.Tabla_Categoria) {
            int row = vista.Tabla_Categoria.rowAtPoint(e.getPoint());
            vista.txt_categoria_id.setText(vista.Tabla_Categoria.getValueAt(row, 0).toString());
            vista.txt_categoria_name.setText(vista.Tabla_Categoria.getValueAt(row, 1).toString());
            vista.btn_registrar_categoria.setEnabled(false);
        } else if (e.getSource() == vista.jLabel8) {
            if (rol.equals("Administrador")) {
                vista.jTabbedPane2.setSelectedIndex(6);
                limpiarCampos();
                limpiarTabla();
                listAllCategorias();
            } else {
                vista.jTabbedPane2.setEnabledAt(6, false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador");
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
        if (e.getSource() == vista.txt_buscar_categoria) {
            limpiarTabla();
            listAllCategorias();
        }
    }

    public void limpiarTabla() {
        model = (DefaultTableModel) vista.Tabla_Categoria.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void listAllCategorias() {
        if (rol.equals("Administrador")) {
            List<Categorias> list_categoria = categoria_dao.listarCategoriaQuery(vista.txt_buscar_categoria.getText());
            model = (DefaultTableModel) vista.Tabla_Categoria.getModel();
            Object[] row = new Object[2];
            for (Categorias cat : list_categoria) {
                row[0] = cat.getId();
                row[1] = cat.getNombre();
                model.addRow(row);
            }
            vista.Tabla_Categoria.setModel(model);
        }
    }

    public void limpiarCampos() {
        vista.txt_categoria_name.setText("");
        vista.txt_categoria_id.setText("");
        vista.btn_registrar_categoria.setEnabled(true);
    }

    public void getCategoriasNombre() {
        vista.cmb_Producto_Categoria.removeAllItems();
        List<Categorias> list = categoria_dao.listarCategoriaQuery("");
        for (Categorias cat : list) {
            vista.cmb_Producto_Categoria.addItem(new ComboBoxDinamico(cat.getId(), cat.getNombre()));
        }
    }
}
