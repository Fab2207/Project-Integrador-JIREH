package Controladores;

import Models.EmpleadosDao;
import static Models.EmpleadosDao.celular_user;
import static Models.EmpleadosDao.correo_user;
import static Models.EmpleadosDao.direccion_user;
import static Models.EmpleadosDao.id_user;
import static Models.EmpleadosDao.nombre_user;
import static Models.EmpleadosDao.rol_user;
import Vistas.Principal;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class SettingsControllers implements MouseListener {

    private Principal view;

    // Colores para hover y por defecto
    private final Color HOVER_COLOR = new Color(152, 202, 63);
    private final Color DEFAULT_COLOR = new Color(18, 45, 61);

    public SettingsControllers(Principal view) {
        this.view = view;

        this.view.jLabel3.addMouseListener(this);  // Productos
        this.view.jLabel4.addMouseListener(this);  // Compras
        this.view.jLabel60.addMouseListener(this); // Ventas
        this.view.jLabel5.addMouseListener(this);  // Clientes
        this.view.jLabel6.addMouseListener(this);  // Empleados
        this.view.jLabel7.addMouseListener(this);  // Proveedores
        this.view.jLabel8.addMouseListener(this);  // Categorías
        this.view.jLabel9.addMouseListener(this);  // Reportes
        this.view.jLabel10.addMouseListener(this); // Configuración

        Perfil();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        cambiarColor(e, HOVER_COLOR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        cambiarColor(e, DEFAULT_COLOR);
    }

    private void cambiarColor(MouseEvent e, Color color) {
        Object source = e.getSource();

        if (source == view.jLabel3) {
            view.jPanelProductos.setBackground(color);
        } else if (source == view.jLabel4) {
            view.jPanelCompras.setBackground(color);
        } else if (source == view.jLabel60) {
            view.jPanelVentas.setBackground(color);
        } else if (source == view.jLabel5) {
            view.jPanelClientes.setBackground(color);
        } else if (source == view.jLabel6) {
            view.jPanelEmpleados.setBackground(color);
        } else if (source == view.jLabel7) {
            view.jPanelProveedores.setBackground(color);
        } else if (source == view.jLabel8) {
            view.jPanelCategorias.setBackground(color);
        } else if (source == view.jLabel9) {
            view.jPanelReportes.setBackground(color);
        } else if (source == view.jLabel10) {
            view.jPanelAjustes.setBackground(color);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();

        if (source == view.jLabel10) { 
            if (rol_user.equalsIgnoreCase("Administrador")) {
                view.jTabbedPane2.setSelectedIndex(8);
            } else {
                view.jTabbedPane2.setEnabledAt(8, false);
                JOptionPane.showMessageDialog(view, "No tienes privilegios de administrador para acceder a la configuración.");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    private void Perfil() {
        this.view.txt_perfil_id.setText("" + id_user);
        this.view.txt_perfil_name.setText(nombre_user);
        this.view.txt_perfil_direccion.setText(direccion_user);
        this.view.txt_perfil_celular.setText(celular_user);
        this.view.txt_perfil_correo.setText(correo_user);
    }

}
