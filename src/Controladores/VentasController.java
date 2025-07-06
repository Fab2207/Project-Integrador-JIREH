package Controladores;

import Models.Clientes;
import Models.ClientesDao;
import Models.EmpleadosDao;
import static Models.EmpleadosDao.rol_user;
import Models.Productos;
import Models.ProductosDao;
import Models.Ventas;
import Models.VentasDao;
import Vistas.Boleta_Venta;
import Vistas.Principal;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentasController implements ActionListener, MouseListener, KeyListener {

    private Ventas venta;
    private VentasDao venta_Dao;
    private Principal vista;
    private Productos producto = new Productos();
    private ProductosDao producto_dao = new ProductosDao();
    private Clientes cliente = new Clientes();
    private ClientesDao cliente_Dao = new ClientesDao();

    private int item = 0;
    private String rol = rol_user;
    private boolean ventaConfirmada = false;

    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel temp = new DefaultTableModel();

    public VentasController(Ventas venta, VentasDao venta_Dao, Principal vista) {
        this.venta = venta;
        this.venta_Dao = venta_Dao;
        this.vista = vista;

        this.vista.btn_agregar_venta_producto.addActionListener(this);
        this.vista.btn_confirmar_venta.addActionListener(this);
        this.vista.btn_eliminar_venta.addActionListener(this);
        this.vista.btn_nueva_venta.addActionListener(this);
        this.vista.Tabla_ventas.addMouseListener(this);
        this.vista.jLabel60.addMouseListener(this);
        this.vista.jLabel9.addMouseListener(this);

        this.vista.txt_venta_producto_code.addKeyListener(this);
        this.vista.txt_venta_precio_producto.addKeyListener(this);
        this.vista.txt_venta_cedula_cliente.addKeyListener(this);
        this.vista.txt_venta_cantidad_producto.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_confirmar_venta) {
            if (ventaConfirmada) {
                JOptionPane.showMessageDialog(null, "La venta ya ha sido confirmada");
                return;
            }

            if (vista.Tabla_ventas.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Debe agregar al menos un producto antes de confirmar la venta");
                return;
            }

            ventaConfirmada = true;
            vista.btn_confirmar_venta.setEnabled(false);
            insertarVenta();

        } else if (e.getSource() == vista.btn_nueva_venta) {
            limpiarAllVentas();
            limpiarTablaTemp();
            ventaConfirmada = false;
            vista.btn_confirmar_venta.setEnabled(true);

        } else if (e.getSource() == vista.btn_eliminar_venta) {
            int selectedRow = vista.Tabla_ventas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) vista.Tabla_ventas.getModel();
            modelo.removeRow(selectedRow);
            calcularVenta();
            vista.txt_venta_producto_code.requestFocus();

        } else if (e.getSource() == vista.btn_agregar_venta_producto) {
            if (vista.txt_venta_producto_name.getText().isEmpty()
                    || vista.txt_venta_cantidad_producto.getText().isEmpty()
                    || vista.txt_venta_precio_producto.getText().isEmpty()
                    || vista.txt_venta_producto_id.getText().isEmpty()
                    || vista.txt_venta_stock_producto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe agregar un producto para continuar con la venta");
                return;
            }

            int cantidad = Integer.parseInt(vista.txt_venta_cantidad_producto.getText());
            int stock = Integer.parseInt(vista.txt_venta_stock_producto.getText());

            if (stock >= cantidad) {
                String producto_nombre = vista.txt_venta_producto_name.getText();
                double precio = Double.parseDouble(vista.txt_venta_precio_producto.getText());
                int producto_id = Integer.parseInt(vista.txt_venta_producto_id.getText());
                double subtotal = cantidad * precio;
                String cliente_nombre = vista.txt_venta_cliente_name.getText();

                temp = (DefaultTableModel) vista.Tabla_ventas.getModel();
                for (int i = 0; i < temp.getRowCount(); i++) {
                    if (temp.getValueAt(i, 1).equals(producto_nombre)) {
                        JOptionPane.showMessageDialog(null, "El producto ya está en la lista");
                        return;
                    }
                }

                Object[] obj = {producto_id, producto_nombre, cantidad, precio, subtotal, cliente_nombre};
                temp.addRow(obj);
                vista.Tabla_ventas.setModel(temp);
                calcularVenta();
                limpiarCamposVentas();
                vista.txt_venta_producto_code.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Stock no disponible");
            }
        }
    }

    private void insertarVenta() {
        int cliente_id = Integer.parseInt(vista.txt_venta_cedula_cliente.getText());
        int empleado_id = EmpleadosDao.id_user;
        double total = Double.parseDouble(vista.txt_venta_monto_total.getText());

        int venta_id = venta_Dao.registrarVentaQuery(cliente_id, empleado_id, total);
        if (venta_id == -1) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la venta");
            return;
        }

        for (int i = 0; i < vista.Tabla_ventas.getRowCount(); i++) {
            int producto_id = Integer.parseInt(vista.Tabla_ventas.getValueAt(i, 0).toString());
            int cantidad = Integer.parseInt(vista.Tabla_ventas.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(vista.Tabla_ventas.getValueAt(i, 3).toString());
            double subtotal = precio * cantidad;

            venta_Dao.registrarVentaDetallesQuery(producto_id, venta_id, cantidad, precio, subtotal);
            producto = producto_dao.buscarId(producto_id);
            int nuevoStock = producto.getCantidad_producto() - cantidad;
            producto_dao.actualizarStockQuery(nuevoStock, producto_id);
        }

        JOptionPane.showMessageDialog(null, "Venta Generada");
        limpiarTablaTemp();
        limpiarAllVentas();
        
        // ✅ AQUÍ SE ABRE LA BOLETA AUTOMÁTICAMENTE
        Boleta_Venta boleta = new Boleta_Venta(venta_id);
        boleta.setVisible(true);
    }

    public void calcularVenta() {
        double total = 0;
        for (int i = 0; i < vista.Tabla_ventas.getRowCount(); i++) {
            total += Double.parseDouble(vista.Tabla_ventas.getValueAt(i, 4).toString());
        }
        vista.txt_venta_monto_total.setText(String.valueOf(total));
    }

    public void limpiarCamposVentas() {
        vista.txt_venta_producto_code.setText("");
        vista.txt_venta_producto_name.setText("");
        vista.txt_venta_cantidad_producto.setText("");
        vista.txt_venta_producto_id.setText("");
        vista.txt_venta_precio_producto.setText("");
        vista.txt_venta_subtotal_producto.setText("");
        vista.txt_venta_stock_producto.setText("");
    }

    public void limpiarAllVentas() {
        limpiarCamposVentas();
        vista.txt_venta_cedula_cliente.setText("");
        vista.txt_venta_cliente_name.setText("");
        vista.txt_venta_monto_total.setText("");
    }

    public void limpiarTablaTemp() {
        temp = (DefaultTableModel) vista.Tabla_ventas.getModel();
        temp.setRowCount(0);
    }

    public void listAllVentas() {
        if (rol.equals("Administrador")) {
            List<Ventas> list = venta_Dao.listaAllVentasQuery();
            model = (DefaultTableModel) vista.Tabla_Reporte_Ventas.getModel();
            model.setRowCount(0);
            Object[] row = new Object[5];
            for (Ventas v : list) {
                row[0] = v.getId();
                row[1] = v.getCliente_nombre();
                row[2] = v.getEmpleado_nombre();
                row[3] = v.getTotal();
                row[4] = v.getFecha_venta();
                model.addRow(row);
            }
            vista.Tabla_Reporte_Ventas.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.jLabel60) {
            vista.jTabbedPane2.setSelectedIndex(2);
        } else if (e.getSource() == vista.jLabel9) {
            if (rol.equals("Administrador")) {
                vista.jTabbedPane2.setSelectedIndex(7);
                listAllVentas();
            } else {
                vista.jTabbedPane2.setEnabledAt(7, false);
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
        if (e.getSource() == vista.txt_venta_producto_code && e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codeText = vista.txt_venta_producto_code.getText();
            if (!codeText.isEmpty()) {
                int code = Integer.parseInt(codeText);
                producto = producto_dao.buscarCodigo(code);
                if (producto.getNombre() != null) {
                    vista.txt_venta_producto_name.setText(producto.getNombre());
                    vista.txt_venta_producto_id.setText(String.valueOf(producto.getId()));
                    vista.txt_venta_stock_producto.setText(String.valueOf(producto.getCantidad_producto()));
                    vista.txt_venta_precio_producto.setText(String.valueOf(producto.getPrecio_unitario()));
                    vista.txt_venta_cantidad_producto.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "No existe ningún producto con ese código");
                    limpiarCamposVentas();
                    vista.txt_venta_producto_code.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el código del producto");
            }
        } else if (e.getSource() == vista.txt_venta_cedula_cliente && e.getKeyCode() == KeyEvent.VK_ENTER) {
            String cedula = vista.txt_venta_cedula_cliente.getText();
            if (!cedula.isEmpty()) {
                int cliente_id = Integer.parseInt(cedula);
                cliente = cliente_Dao.buscarClientes(cliente_id);
                if (cliente.getNombre_completo() != null) {
                    vista.txt_venta_cliente_name.setText(cliente.getNombre_completo());
                } else {
                    vista.txt_venta_cedula_cliente.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txt_venta_cantidad_producto) {
            double precio = 0;
            int cantidad = 1;
            if (!vista.txt_venta_precio_producto.getText().isEmpty()) {
                precio = Double.parseDouble(vista.txt_venta_precio_producto.getText());
            }
            if (!vista.txt_venta_cantidad_producto.getText().isEmpty()) {
                cantidad = Integer.parseInt(vista.txt_venta_cantidad_producto.getText());
            }
            vista.txt_venta_subtotal_producto.setText(String.valueOf(cantidad * precio));
        }
    }
}