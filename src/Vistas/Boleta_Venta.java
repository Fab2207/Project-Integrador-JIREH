package Vistas;

import Models.Ventas;
import Models.VentasDao;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Boleta_Venta extends javax.swing.JFrame {

    Ventas venta = new Ventas();
    VentasDao venta_dao = new VentasDao();
    DefaultTableModel model = new DefaultTableModel();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Boleta_Venta.class.getName());

    /**
     * Creates new form Boleta_Venta
     */
    public Boleta_Venta(int id) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Boleta de Venta");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        txt_boleta_numero.setText("" + id);
        listAllVentasDetalles(id);
        calcularVenta();
    }

    public void listAllVentasDetalles(int id) {
        List<Ventas> list = venta_dao.listaVentasDetallesQuery(id);
        model = (DefaultTableModel) ventas_detalles_tabla.getModel();
        Object[] row = new Object[6];
        
        // Variables para almacenar datos del cliente (se llenan con el primer registro)
        boolean datosClienteLlenados = false;
        
        for (int i = 0; i < list.size(); i++) {
            Ventas ventaDetalle = list.get(i);
            
            // Llenar datos del cliente solo una vez
            if (!datosClienteLlenados) {
                txt_cliente_dni.setText(String.valueOf(ventaDetalle.getCliente_id()));
                txt_cliente_nombre.setText(ventaDetalle.getCliente_nombre());
                datosClienteLlenados = true;
            }
            
            row[0] = ventaDetalle.getProducto_nombre();
            row[1] = ventaDetalle.getVenta_cantidad();
            row[2] = ventaDetalle.getVenta_precio();
            row[3] = ventaDetalle.getVenta_subtotal();
            row[4] = ventaDetalle.getEmpleado_nombre();
            row[5] = ventaDetalle.getFecha_venta();
            model.addRow(row);
        }
        ventas_detalles_tabla.setModel(model);
    }

    public void calcularVenta() {
        double total = 0;
        int numRow = ventas_detalles_tabla.getRowCount();
        for (int i = 0; i < numRow; i++) {
            total = total + Double.parseDouble(String.valueOf(ventas_detalles_tabla.getValueAt(i, 3)));
        }
        txt_total.setText("" + total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form_print = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_boleta_numero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_cliente_dni = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_cliente_nombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ventas_detalles_tabla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_imprimir_boleta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(620, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        form_print.setBackground(new java.awt.Color(152, 202, 63));
        form_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/face.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        form_print.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        jPanel2.setBackground(new java.awt.Color(18, 45, 61));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Farmacia JIREH");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        txt_boleta_numero.setEditable(false);
        txt_boleta_numero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txt_boleta_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 110, 25));

        form_print.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 70));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DNI:");
        form_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        txt_cliente_dni.setEditable(false);
        txt_cliente_dni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        form_print.add(txt_cliente_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 150, 25));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cliente:");
        form_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));

        txt_cliente_nombre.setEditable(false);
        txt_cliente_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        form_print.add(txt_cliente_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 200, 25));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Detalles de la Venta");
        form_print.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        ventas_detalles_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Precio", "Subtotal", "Empleado", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ventas_detalles_tabla);
        if (ventas_detalles_tabla.getColumnModel().getColumnCount() > 0) {
            ventas_detalles_tabla.getColumnModel().getColumn(0).setMinWidth(100);
            ventas_detalles_tabla.getColumnModel().getColumn(4).setMinWidth(110);
            ventas_detalles_tabla.getColumnModel().getColumn(5).setMinWidth(80);
        }

        form_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 560, 150));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total:");
        form_print.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        form_print.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 120, -1));

        getContentPane().add(form_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 520));

        btn_imprimir_boleta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_imprimir_boleta.setText("Imprimir");
        btn_imprimir_boleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimir_boletaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_imprimir_boleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 540, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimir_boletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimir_boletaActionPerformed
        Toolkit tk = form_print.getToolkit();
        PrintJob pj = tk.getPrintJob(this, null, null);
        Graphics grafic = pj.getGraphics();
        form_print.print(grafic);
        grafic.dispose();
        pj.end();
    }//GEN-LAST:event_btn_imprimir_boletaActionPerformed

    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_imprimir_boleta;
    private javax.swing.JPanel form_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_boleta_numero;
    private javax.swing.JTextField txt_cliente_dni;
    private javax.swing.JTextField txt_cliente_nombre;
    public javax.swing.JTextField txt_total;
    public javax.swing.JTable ventas_detalles_tabla;
    // End of variables declaration//GEN-END:variables
}